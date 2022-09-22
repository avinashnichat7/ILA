package com.neo.v1.model.currency;

import com.neo.core.model.ResponseMeta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {

    private ResponseMeta meta;

    private CurrencyData data;
}
