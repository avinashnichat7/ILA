package com.neo.v1.model.charity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharityItemData {

    private java.math.BigDecimal donatedAmount;

    private List<CharityItem> charities;
}
