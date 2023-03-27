package com.neo.v1.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Builder
@Getter
@Setter
@Entity
@Table(name = "language")
@NoArgsConstructor
@AllArgsConstructor

public class LanguageEntity {

    @Id
    private Long id;

    private String code;

    private String name;
}
