package com.tanda.paymentgateway.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;


public class CallBackRes {
    @NotNull
    @JsonProperty("Result")
    public Result result;
    public static class ReferenceData {
        @JsonProperty("ReferenceItem")
        public ReferenceItem referenceItem;
    }

    public static class ReferenceItem {
        @JsonProperty("Key")
        public String key;
        @JsonProperty("Value")
        public String value;
    }

    public static class Result {
        @JsonProperty("ResultType")
        public int resultType;
        @NotEmpty
        @JsonProperty("ResultCode")
        public int resultCode;
        @JsonProperty("ResultDesc")
        public String resultDesc;
        @JsonProperty("OriginatorConversationID")
        public String originatorConversationID;
        @NotEmpty
        @JsonProperty("ConversationID")
        public String conversationID;
        @NotEmpty
        @JsonProperty("TransactionID")
        public String transactionID;
        @JsonProperty("ResultParameters")
        public ResultParameters resultParameters;
        @JsonProperty("ReferenceData")
        public ReferenceData referenceData;
    }

    public static class ResultParameter {
        @JsonProperty("Key")
        public String key;
        @JsonProperty("Value")
        public Object value;
    }

    public static class ResultParameters {
        @JsonProperty("ResultParameter")
        public ArrayList<ResultParameter> resultParameter;
    }

}



