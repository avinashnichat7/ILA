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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Builder
@Getter
@Setter
@Entity
@Table(name = "customer_merchant_category")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMerchantCategoryEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "SEQUENCE_MERCHANT_CATEGORY")
    @SequenceGenerator(name = "SEQUENCE_MERCHANT_CATEGORY", sequenceName = "seq_customer_merchant_category")
    private Long id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CustomerCategoryEntity customerCategory;

    @Column(name = "is_custom")
    private boolean isCustom;

    @Column(name = "active")
    private boolean active;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
