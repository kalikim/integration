package com.tanda.paymentgateway.integration.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "transactions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "org_conv_id")
    public String originatorConversationID;
    @Column(name = "conv_id")
    public String conversationID;
    @Column(name = "res_description")
    public String responseDescription;
    @Column(name = "res_payload", length = 2000)
    public String responsePayload;
    @Column(name = "req_payload", length = 2000)
    public String requestPayload;
}
