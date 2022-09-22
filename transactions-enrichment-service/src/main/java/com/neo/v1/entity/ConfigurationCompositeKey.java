package com.neo.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class ConfigurationCompositeKey implements Serializable {

    private static final long serialVersionUID = 664804303484160101L;

    @Column(name = "unit_id")
    private Integer unitId;

    @Column(name = "config_code")
    private String configCode;
}
