package com.polytech.greenhouse.service.impl;

import com.polytech.greenhouse.config.RabbitMQConfig;
import com.polytech.greenhouse.entity.Measurement;
import com.polytech.greenhouse.entity.Parameter;
import com.polytech.greenhouse.repository.MeasurementRepository;
import com.polytech.greenhouse.repository.ParameterRepository;
import com.polytech.greenhouse.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final ParameterRepository parameterRepository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Measurement recordMeasurement(Measurement measurement) {
        measurement.setTimestamp(LocalDateTime.now());

        Optional<Parameter> paramOpt = parameterRepository.findByType(measurement.getType());

        if (paramOpt.isPresent()) {
            Parameter param = paramOpt.get();
            measurement.setParameterId(param.getId());

            // --- ALERTING LOGIC START ---
            checkThresholdsAndAlert(measurement, param);
            // --- ALERTING LOGIC END ---
        }

        return measurementRepository.save(measurement);
    }

    private void checkThresholdsAndAlert(Measurement m, Parameter p) {
        String alertMessage = null;

        if (m.getValue() > p.getMaxThreshold()) {
            alertMessage = String.format("ALERT: %s too high! Value: %.2f > Max: %.2f",
                    m.getType(), m.getValue(), p.getMaxThreshold());
        } else if (m.getValue() < p.getMinThreshold()) {
            alertMessage = String.format("ALERT: %s too low! Value: %.2f < Min: %.2f",
                    m.getType(), m.getValue(), p.getMinThreshold());
        }

        if (alertMessage != null) {
            // Send message to RabbitMQ
            // Routing key example: "alert.TEMPERATURE"
            String routingKey = "alert." + m.getType().name();
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, alertMessage);

            log.info(" [x] Sent RabbitMQ Message: {} '", alertMessage);
        }
    }

    @Override
    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }
}