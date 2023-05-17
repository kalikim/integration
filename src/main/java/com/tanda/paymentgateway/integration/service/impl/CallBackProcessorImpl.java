package com.tanda.paymentgateway.integration.service.impl;

import com.tanda.paymentgateway.integration.db.model.Log;
import com.tanda.paymentgateway.integration.db.repo.TransactionRepo;
import com.tanda.paymentgateway.integration.model.CallBackRes;
import com.tanda.paymentgateway.integration.model.GwRequest;
import com.tanda.paymentgateway.integration.model.QwResult;
import com.tanda.paymentgateway.integration.model.Status;
import com.tanda.paymentgateway.integration.service.CallBackProcessor;
import com.tanda.paymentgateway.integration.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class CallBackProcessorImpl implements CallBackProcessor {
    private final LogService logService;
    @Qualifier(value = "kafkaTemplateQwResult")
    private final KafkaTemplate<String, QwResult> kafkaTemplate;
    private final TransactionRepo transactionRepo;

    @Override
    public void processCallBack(CallBackRes callBackRes) {
        var ref = callBackRes.result.conversationID;
        Log log_ = logService.getLogByRef(ref);
        if (Objects.nonNull(log)) {
            var transaction = transactionRepo.getTransactionByConversationID(ref);
            if (Objects.nonNull(transaction)) {
                //successfully done payment delete transaction request
                transactionRepo.delete(transaction);
                if (callBackRes.result.conversationID.equalsIgnoreCase("0")){
                    log_.setStatus(Status.SUCCESS.name());
                }
                log_.setTransactionId(callBackRes.result.transactionID);
                logService.saveLog(log_);
                //package result send to kafka
                var qwResult = QwResult.builder()
                        .id(UUID.fromString(log_.getLogId()))
                        .ref(callBackRes.result.transactionID)
                        .status(Status.COMPLETED)
                        .resultCode(callBackRes.result.resultCode)
                        .build();
                kafkaTemplate.send("integration_tanda_cps_callback", qwResult);
            } else {
                //non existing transaction that was logged
              if(Objects.nonNull(log_)) logService.deleteLog(log_);
            }
        } else {
            log.info("Invalid Ref {}", ref);
        }
    }
}
