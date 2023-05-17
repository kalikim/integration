package com.tanda.paymentgateway.integration.model;


import lombok.Data;

@Data
public class TokenReq {
    public String access_token;
    public String expires_in;
}
