package com.tanda.paymentgateway.integration;

import com.tanda.paymentgateway.integration.model.GwRequest;
import com.tanda.paymentgateway.integration.service.GwRequestValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GwRequestValidatorImplTest {
    @Autowired
    private GwRequestValidator validator;

    @Test
    void testIsValidGwRequest_ValidRequest_ReturnsTrue() {
        // Arrange

        GwRequest gwRequest = GwRequest.builder()
                .mobileNumber("0712345678")
                .amount(100.0f)
                .build();

        // Act
        boolean isValid = validator.isValidGwRequest(gwRequest);

        // Assert
        assertTrue(isValid);
    }
    @Test
    void testIsValidGwRequest_InvalidPhoneNumber_ReturnsFalse() {
        // Arrange

        GwRequest gwRequest = GwRequest.builder()
                .mobileNumber("0123456789")
                .amount(100.0f)
                .build();


        // Act
        boolean isValid = validator.isValidGwRequest(gwRequest);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void testIsValidGwRequest_InvalidAmount_ReturnsFalse() {
        // Arrange
        GwRequest gwRequest = GwRequest.builder()
                .mobileNumber("0712345678")
                .amount(5.0f)
                .build();

        // Act
        boolean isValid = validator.isValidGwRequest(gwRequest);

        // Assert
        assertFalse(isValid);
    }


}