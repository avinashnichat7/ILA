package com.neo.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@Entity
@Table(name = "transfer_operation_type")
@NoArgsConstructor
@AllArgsConstructor
public class TransferOperationTypeEntity {
    @Id
    private Long id;

    @Column(name = "operation_type")
    private String operationType;
}
