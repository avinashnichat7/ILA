package com.neo.v1.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neo.core.model.ResponseMeta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDetailResponse {

    private CustomerDetailData data;
    private ResponseMeta meta;
}
