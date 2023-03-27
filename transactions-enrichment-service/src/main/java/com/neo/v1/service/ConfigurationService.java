package com.neo.v1.service;


import java.util.*;
import javax.annotation.PostConstruct;

import com.neo.v1.reader.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neo.v1.entity.ConfigurationEntity;
import com.neo.v1.enums.GlobalConfig;
import com.neo.v1.repository.ConfigurationRepository;

import lombok.RequiredArgsConstructor;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;
    private final PropertyReader propertyReader;

    List<ConfigurationEntity> configList = new ArrayList<>();

    @PostConstruct
    @Transactional(readOnly = true)
    public void loadConfigurations() {
        configList = configurationRepository.findAll();
    }

    public String getConfiguration(GlobalConfig config) {
        String unit = StringUtils.isBlank(getContext().getUnit()) ? propertyReader.getDefaultUnit() : getContext().getUnit();
        return configList.stream()
                .filter(entity -> StringUtils.equals(entity.getConfigurationIdentity().getUnit().getCode(), unit))
                .filter(entity -> StringUtils.equals(entity.getConfigurationIdentity().getConfigCode(), config.name()))
                .map(ConfigurationEntity::getValue)
                .findFirst().orElse(null);
    }
}