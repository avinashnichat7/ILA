package com.neo.v1.model.charity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.neo.core.serialization.CustomLocalDateTimeDeserializer;
import com.neo.core.serialization.CustomLocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurposeItem {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private String icon;

    private String logo;

    private java.math.BigDecimal amountContributed;

    private Long goalPercentageCompleted;

    private java.math.BigDecimal goalTargetAmount;

    @NotNull
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime startDate;

    @NotNull
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime expiryDate;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime goalStartDate;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime goalExpiryDate;

    @NotNull
    private Long order;

    private String description;

    private String code;
}
