package com.neo.v1.model.tmsx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("BillDetails")
public class BillDetail {

    @ApiModelProperty(notes = "Biller Name")
    private String billerName;

    @ApiModelProperty(notes = "Service Name")
    private String serviceName;

    @ApiModelProperty(notes = "Service")
    private String service;

    @ApiModelProperty(notes = "Channel")
    private String channel;

    @ApiModelProperty(notes = "Payment Method")
    private String paymentMethod;

    @ApiModelProperty(notes = "Subscriber Identification")
    private SubscriberIdentification subscriberIdentification;

    @ApiModelProperty(notes = "Operator")
    private String operator;

    @ApiModelProperty(notes = "Payment Acceptance Outlet Code")
    private String paymentAcceptanceOutletCode;

    @ApiModelProperty(notes = "Bill Reference")
    private String billReference;

    @ApiModelProperty(notes = "Custom Feilds")
    private List<CustomFeild> customFields;

}
