package com.neo.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@Entity
@Table(name = "configuration")
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationEntity {

    @EmbeddedId
    private ConfigurationCompositeKey configurationCompositeKey;

    private String value;

    private String description;
}
