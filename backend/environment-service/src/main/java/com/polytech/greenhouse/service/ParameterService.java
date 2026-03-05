package com.polytech.greenhouse.service;

import com.polytech.greenhouse.entity.Parameter;
import com.polytech.greenhouse.enums.EnvType;

public interface ParameterService {
    Parameter createOrUpdateParameter(Parameter parameter);
    Parameter getParameterByType(EnvType type);
}