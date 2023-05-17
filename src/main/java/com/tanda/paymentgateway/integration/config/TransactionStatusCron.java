package com.tanda.paymentgateway.integration.config;


import com.tanda.paymentgateway.integration.db.model.Log;
import com.tanda.paymentgateway.integration.model.B2CTransactionStatusReq;
import com.tanda.paymentgateway.integration.model.QwResult;
import com.tanda.paymentgateway.integration.model.Status;
import com.tanda.paymentgateway.integration.service.LogService;
import com.tanda.paymentgateway.integration.service.MpesaDaraja;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@EnableScheduling
@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionStatusCron {

    private final LogService logService;
    private final MpesaDaraja mpesaDaraja;
    @Qualifier(value = "kafkaTemplateQwResult")
    private final KafkaTemplate<String, QwResult> kafkaTemplate;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 0/1 * ? * *")
    public void getTransactionStatus() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        Set<Log> logs = logService.getPendingLogs(Status.PENDING.name());
        logs.forEach(log -> {
            //call Mpesa Daraja Transaction API
            var b2cTransactionStatusReq = B2CTransactionStatusReq.builder().initiatorName("testapi").securityCredential("p122RtzObP48Kiy35psRHhO0yDjqiKz6PQDfHnlzOycavADGdw0iS870sYPL3mbdC06gVsHbt7Xadnc1Kxlyd0g1AC0vglj3T7UO1bxn6KJe3ouScvj3WdEkuaHWT7rZBwvGi/+N2OX4E9DxALlb0KT5fytqn4DP6p60nYgAMqLu5AML/jWtvKz0WrsnnbBZedkO8sqcBx3DLAZJLaJzzzABrG18DV3Th+dJOkEnbUHRxeEsAb7YYROQPRBpfqcfMuRA+16zpfp3bakp9vhv6l8wp6btA+WW7aJJlJ3QvMB2iL15L+hqSo7CRT4dI8ziW673IXdKkvQ1H1oEoOm2fg==").commandID("TransactionStatusQuery").partyA("600584").identifierType(1).transactionID("OEI2AK4Q16").remarks(String.format("Get Transaction status for %s", log.getLogId())).queueTimeOutURL("https://682f-197-232-61-250.ngrok-free.app").resultURL("https://682f-197-232-61-250.ngrok-free.app").occassion("Salary Payment").build();
            if (Objects.nonNull(log.getTransactionId())) {
                mpesaDaraja.getTransactionStatus(b2cTransactionStatusReq).subscribe(b2CRes -> {
                    log.setRetryCount(log.getRetryCount() + 1);
                    var qwResult = QwResult.builder().id(UUID.fromString(log.getLogId())).ref(b2CRes.ConversationID).status(Status.COMPLETED).resultCode(Integer.valueOf(b2CRes.ResponseCode)).build();
                    if (b2CRes.ResponseCode.equalsIgnoreCase("0")) {
                        log.setStatus(Status.SUCCESS.name());
                        logService.saveLog(log);
                    } else {
                        log.setStatus(Status.FAILED.name());
                        logService.saveLog(log);
                    }
                    kafkaTemplate.send("integration_tanda_cps_callback", qwResult);
                });
            }
        });
    }
}
