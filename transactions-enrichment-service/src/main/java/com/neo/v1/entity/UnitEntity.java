package com.neo.v1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@Table(name = "unit")
@NoArgsConstructor
@AllArgsConstructor
public class UnitEntity {

    @Id
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "time_zone")
    private String timeZone;
    @Column(name = "currency")
    private String currency;

    @Column(name = "currency_decimal_places")
    private String currencyDecimalPlaces;
    @OneToOne
    @JoinColumn(name = "default_language")
    private LanguageEntity defaultLanguage;
}


