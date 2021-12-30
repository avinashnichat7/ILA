package com.neo.v1.tmsx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel("SubscriberIdentification")
public class SubscriberIdentification {
    @ApiModelProperty(notes = "SubscriberIdentification type")
    private String type;

    @ApiModelProperty(notes = "SubscriberIdentification value")
    private String value;
}
