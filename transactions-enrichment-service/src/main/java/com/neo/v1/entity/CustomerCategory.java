package com.neo.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Builder
@Getter
@Setter
@Entity
@Table(name = "customer_category")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCategory {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "SEQUENCE_CUSTOMER_CATEGORY")
    @SequenceGenerator(name = "SEQUENCE_CUSTOMER_CATEGORY", sequenceName = "seq_customer_category")
    private Long id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "color")
    private String color;

    @Column(name = "active")
    private boolean active;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
