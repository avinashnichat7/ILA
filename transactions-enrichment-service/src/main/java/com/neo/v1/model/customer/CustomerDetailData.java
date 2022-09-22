package com.neo.v1.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neo.v1.enums.customer.RecordType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDetailData {

    private String customerId;
    private RecordType recordType;
}
