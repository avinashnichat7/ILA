package com.neo.v1.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationIdentity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitEntity unit;

    @Column(name = "config_code")
    private String configCode;
}