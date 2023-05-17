package com.tanda.paymentgateway.integration.service;

import com.tanda.paymentgateway.integration.model.B2CReq;
import com.tanda.paymentgateway.integration.model.B2CRes;
import com.tanda.paymentgateway.integration.model.B2CTransactionStatusReq;
import reactor.core.publisher.Mono;

public interface MpesaDaraja {
    B2CRes makeB2C(final B2CReq b2CReq);
    Mono<B2CRes> getTransactionStatus(final B2CTransactionStatusReq b2CTransactionStatusReq);
}
