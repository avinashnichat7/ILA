package com.neo.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@Entity
@Table(name = "tmsx_urbis_transfer_operation_type")
@NoArgsConstructor
@AllArgsConstructor
public class TmsxUrbisOperationTypesEntity {

    @Id
    private Long id;

    private String tmsx;

    private String urbis;

    private String vat;

    private String charges;

    private String description;

    @Column(name = "min_amount")
    private double minAmount;

    @Column(name = "max_amount")
    private double maxAmount;

    @Column(name = "transaction_type_code")
    private String transactionTypeCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "operation_type_id")
    private TransferOperationTypeEntity transferOperationTypeEntity;
}
