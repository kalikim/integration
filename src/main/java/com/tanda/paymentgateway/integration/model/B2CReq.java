package com.tanda.paymentgateway.integration.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class B2CReq {
    @JsonProperty("InitiatorName")
    public String initiatorName;
    @JsonProperty("SecurityCredential")
    public String securityCredential;
    @JsonProperty("CommandID")
    public String commandID;
    @JsonProperty("Amount")
    public String amount;
    @JsonProperty("PartyA")
    public String partyA;
    @JsonProperty("PartyB")
    public String partyB;
    @JsonProperty("Remarks")
    public String remarks;
    @JsonProperty("QueueTimeOutURL")
    public String queueTimeOutURL;
    @JsonProperty("ResultURL")
    public String resultURL;
    @JsonProperty("Occassion")
    public String occassion;
}
