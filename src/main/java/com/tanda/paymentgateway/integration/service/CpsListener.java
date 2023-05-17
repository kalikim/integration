package com.tanda.paymentgateway.integration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanda.paymentgateway.integration.db.model.Log;
import com.tanda.paymentgateway.integration.db.model.Transaction;
import com.tanda.paymentgateway.integration.db.repo.TransactionRepo;
import com.tanda.paymentgateway.integration.model.B2CReq;
import com.tanda.paymentgateway.integration.model.GwRequest;
import com.tanda.paymentgateway.integration.model.Status;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

// Listen from Kafka Topic (subscribe to Kafka Topic)
@Component
@Slf4j
@RequiredArgsConstructor
public class CpsListener {

    private final GwRequestValidator gwRequestValidator;
    private final LogService logService;
    private final MpesaDaraja mpesaDaraja;
    private final TransactionRepo transactionRepo;
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "integration_tanda_cps", groupId = "group_id", containerFactory = "gwRequestListener")
    void listener(@Payload GwRequest data) throws JsonProcessingException {
        log.info("GwRequest from CPS {}", data);
        if (gwRequestValidator.isValidGwRequest(data)) {
            var logToSave = Log.builder().retryCount(0).logId(data.getId().toString()).mobileNumber(data.getMobileNumber()).status(Status.PENDING.name()).amount(data.getAmount()).build();
            //Write to db
            Log logSaved = logService.saveLog(logToSave);
            //make payment req to daraja
            var b2cReq = B2CReq.builder().initiatorName("testapi").securityCredential("JylbuT8nj60OYRLBueDSqSXFsU2hc0XHmM9XHwm9vUZsVDvfeLhZ8kpIaMFaHlTY8yDHHIcZsqkRQpfd2JTNFsGGOPzmIVBMMyXJXmMxpvB92mwE83QruBV3I/Vsi204gyBSHHsLwvC/w3V1r04piCWg2m2Q6BY5nMaUb+cDqix+3oWVGI3Qj5Li6/Sv4EKK8JRgLSRWlFZqoN703d0Q9isd8qqN9hTVL9Rgp31nhLtm8x/dxV4j2L2vZv66EF5RW9as9lwyQPPF353Z5ClTmdYpefZwyQcYO1RxQBR/HxRLGg2wpS9OXtuBRTbVGabO0WQR2pUioT+WLFtClHJl8g==").commandID("BusinessPayment").partyA("600990").partyB(data.getMobileNumber()).remarks(String.format("Confirm Salary Recipient %s", data.getId().toString())).queueTimeOutURL("https://682f-197-232-61-250.ngrok-free.app").resultURL("https://682f-197-232-61-250.ngrok-free.app").occassion("Salary Payment").amount(Float.toString(data.getAmount())).build();
            var b2CRes = mpesaDaraja.makeB2C(b2cReq);
            log.info("B2C Response {} ", b2CRes);
            if (Objects.nonNull(b2CRes)) {
                logSaved.setRef(b2CRes.getConversationID());
                logService.saveLog(logSaved);
                //save transaction request
                transactionRepo.save(Transaction.builder().responseDescription(b2CRes.getResponseDescription()).originatorConversationID(b2CRes.getOriginatorConverstionID()).conversationID(b2CRes.getConversationID())
                        .requestPayload(mapper.writeValueAsString(b2cReq))
                        .responsePayload(mapper.writeValueAsString(b2CRes)).build());
            } else {
                logService.saveLog(logSaved);
                logSaved.setStatus(Status.FAILED.name());
            }
        } else {
            log.debug(" Amount {} or PhoneNumber {} is invalid", data.getAmount(), data.getMobileNumber());
        }
    }
}
