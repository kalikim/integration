package com.tanda.paymentgateway.integration.service;

import com.tanda.paymentgateway.integration.model.GwRequest;

public interface GwRequestValidator {

    boolean isValidGwRequest(GwRequest gwRequest);
}
