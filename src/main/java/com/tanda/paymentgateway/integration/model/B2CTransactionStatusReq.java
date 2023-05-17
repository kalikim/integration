package com.tanda.paymentgateway.integration.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class B2CTransactionStatusReq {
    @JsonProperty("InitiatorName")
    public String initiatorName;
    @JsonProperty("SecurityCredential")
    public String securityCredential;
    @JsonProperty("CommandID")
    public String commandID;
    @JsonProperty("PartyA")
    public String partyA;
    @JsonProperty("IdentifierType")
    public Integer identifierType;
    @JsonProperty("TransactionID")
    public String transactionID;
    @JsonProperty("ResultURL")
    public String resultURL;
    @JsonProperty("QueueTimeOutURL")
    public String queueTimeOutURL;
    @JsonProperty("Remarks")
    public String remarks;
    @JsonProperty("Occassion")
    public String occassion;
}