package com.polytech.greenhouse.service.impl;

import com.polytech.greenhouse.entity.Parameter;
import com.polytech.greenhouse.enums.EnvType;
import com.polytech.greenhouse.repository.ParameterRepository;
import com.polytech.greenhouse.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParameterServiceImpl implements ParameterService {

    private final ParameterRepository parameterRepository;

    @Override
    public Parameter createOrUpdateParameter(Parameter parameter) {
        // Check if parameter for this type already exists to update it instead of creating duplicate
        return parameterRepository.findByType(parameter.getType())
                .map(existing -> {
                    existing.setMinThreshold(parameter.getMinThreshold());
                    existing.setMaxThreshold(parameter.getMaxThreshold());
                    return parameterRepository.save(existing);
                })
                .orElseGet(() -> parameterRepository.save(parameter));
    }

    @Override
    public Parameter getParameterByType(EnvType type) {
        return parameterRepository.findByType(type)
                .orElseThrow(() -> new RuntimeException("Parameter configuration not found for type: " + type));
    }
}