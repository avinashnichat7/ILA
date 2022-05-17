package com.neo.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Builder
@Getter
@Setter
@Entity
@Table(name = "customer_account_transaction_category")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAccountTransactionCategoryEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "SEQUENCE_CUSTOMER_ACCOUNT_TRANSACTION_CATEGORY")
    @SequenceGenerator(name = "SEQUENCE_CUSTOMER_ACCOUNT_TRANSACTION_CATEGORY", sequenceName = "seq_customer_account_transaction_category")
    private Long id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "transaction_reference")
    private String transactionReference;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "is_custom")
    private boolean isCustom;

    @Column(name = "active")
    private boolean active;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
