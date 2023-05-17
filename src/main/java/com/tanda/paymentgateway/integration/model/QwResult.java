package com.tanda.paymentgateway.integration.model;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class QwResult {
    //write to kafka for CPS to complete processing
    private final UUID id;
    private final Status status;
    private final String ref;
    private final Integer resultCode;
}
