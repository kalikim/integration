package com.tanda.paymentgateway.integration;

import com.tanda.paymentgateway.integration.model.B2CReq;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class B2CReqTest {

    @Test
    public void testB2CReqProperties() {
        // Create an instance of B2CReq
        B2CReq b2CReq = B2CReq.builder()
                .initiatorName("John Doe")
                .securityCredential("password123")
                .commandID("CMD001")
                .amount("100")
                .partyA("Sender")
                .partyB("Receiver")
                .remarks("Payment")
                .queueTimeOutURL("http://example.com/timeout")
                .resultURL("http://example.com/result")
                .occassion("Occasion")
                .build();

        // Test the property values
        assertEquals("John Doe", b2CReq.getInitiatorName());
        assertEquals("password123", b2CReq.getSecurityCredential());
        assertEquals("CMD001", b2CReq.getCommandID());
        assertEquals("100", b2CReq.getAmount());
        assertEquals("Sender", b2CReq.getPartyA());
        assertEquals("Receiver", b2CReq.getPartyB());
        assertEquals("Payment", b2CReq.getRemarks());
        assertEquals("http://example.com/timeout", b2CReq.getQueueTimeOutURL());
        assertEquals("http://example.com/result", b2CReq.getResultURL());
        assertEquals("Occasion", b2CReq.getOccassion());
    }
}
