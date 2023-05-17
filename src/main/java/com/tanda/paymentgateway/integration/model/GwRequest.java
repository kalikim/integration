package com.tanda.paymentgateway.integration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GwRequest {
    protected UUID id;
    protected Float amount;
    protected String mobileNumber;
}
