package com.tanda.paymentgateway.integration.service;

import com.tanda.paymentgateway.integration.model.CallBackRes;

public interface CallBackProcessor {
    void processCallBack(CallBackRes callBackRes);
}
