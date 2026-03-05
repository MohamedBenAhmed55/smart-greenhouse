package com.polytech.greenhouse.repository;

import com.polytech.greenhouse.entity.Parameter;
import com.polytech.greenhouse.enums.EnvType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    Optional<Parameter> findByType(EnvType type);
}