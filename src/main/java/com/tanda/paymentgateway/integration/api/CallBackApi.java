package com.tanda.paymentgateway.integration.api;


import com.tanda.paymentgateway.integration.model.CallBackRes;
import com.tanda.paymentgateway.integration.service.CallBackProcessor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CallBackApi {

    private final CallBackProcessor callBackProcessor;
    @PostMapping("b2c")
    public ResponseEntity<String> paymentCallBack(@Valid @RequestBody CallBackRes callBackRes){
        callBackProcessor.processCallBack(callBackRes);
        return ResponseEntity.ok("Received Successfully");
    }
}
