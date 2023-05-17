package com.tanda.paymentgateway.integration.service.impl;


import com.tanda.paymentgateway.integration.model.B2CReq;
import com.tanda.paymentgateway.integration.model.B2CRes;
import com.tanda.paymentgateway.integration.model.B2CTransactionStatusReq;
import com.tanda.paymentgateway.integration.model.RegisterUrlReq;
import com.tanda.paymentgateway.integration.model.TokenReq;
import com.tanda.paymentgateway.integration.service.MpesaDaraja;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpesaDarajaImpl implements MpesaDaraja {

    private final String consumer_key = "B4zYudSMdm3osEQPC8MwxGnz09bRBKP7";
    private final String consumer_secret = "w3OucdFUdeza5km1";
    private final WebClient webClient = WebClient.create();

    private TokenReq getToken() {

        Mono<TokenReq> response = webClient.get()
                .uri("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
                .headers(httpHeaders -> httpHeaders.setBasicAuth(consumer_key, consumer_secret))
                .retrieve()
                .bodyToMono(TokenReq.class);
        response.subscribe(System.out::println);
        return response.block();
    }

    private void registerUrl(final String shortCode, final String token) {
        TokenReq tokenReq = getToken();
        Mono<String> b2cResponse = webClient.post()
                .uri("hhttps://sandbox.safaricom.co.ke/mpesa/c2b/v1/registerurl")
                .body(BodyInserters.fromValue(RegisterUrlReq.builder()
                        .shortCode(shortCode)
                        .confirmationURL("https://682f-197-232-61-250.ngrok-free.app")
                        .responseType("Completed")
                        .validationURL("https://682f-197-232-61-250.ngrok-free.app")
                        .build()))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .retrieve()
                .bodyToMono(String.class);
        b2cResponse.subscribe(System.out::println);
    }

    @Override
    public B2CRes makeB2C(B2CReq b2CReq) {
        TokenReq tokenReq = getToken();
        var token = tokenReq.getAccess_token();
        // registerUrl(b2CReq.getPartyA(), token);
        Mono<B2CRes> b2cResponse = webClient.post()
                .uri("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest")
                .body(BodyInserters.fromValue(b2CReq))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .retrieve()
                .bodyToMono(B2CRes.class);
        b2cResponse.subscribe(System.out::println);
        return b2cResponse.block();
    }

    @Override
    public Mono<B2CRes> getTransactionStatus(B2CTransactionStatusReq b2CTransactionStatusReq) {
        TokenReq tokenReq = getToken();
        var token = tokenReq.getAccess_token();
        Mono<B2CRes> b2cTransactionStatusResponse = webClient.post()
                .uri("https://sandbox.safaricom.co.ke/mpesa/transactionstatus/v1/query")
                .body(BodyInserters.fromValue(b2CTransactionStatusReq))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .retrieve()
                .bodyToMono(B2CRes.class);
        return b2cTransactionStatusResponse;
    }


}
