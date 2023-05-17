package com.tanda.paymentgateway.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUrlReq {
    @JsonProperty("ShortCode")
    public String shortCode;
    @JsonProperty("ResponseType")
    public String responseType;
    @JsonProperty("ConfirmationURL")
    public String confirmationURL;
    @JsonProperty("ValidationURL")
    public String validationURL;
}
