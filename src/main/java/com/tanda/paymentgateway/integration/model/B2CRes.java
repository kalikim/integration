package com.tanda.paymentgateway.integration.model;

import lombok.Data;

@Data
public class B2CRes {
    public String OriginatorConverstionID;
    public String ConversationID;
    public String ResponseDescription;
    public String  ResponseCode;
}
