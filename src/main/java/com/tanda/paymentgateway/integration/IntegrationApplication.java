package com.tanda.paymentgateway.integration;

import com.tanda.paymentgateway.integration.model.GwRequest;
import com.tanda.paymentgateway.integration.model.QwResult;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@SpringBootApplication
public class IntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntegrationApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(KafkaTemplate<String, GwRequest> kafkaTemplate) {
        return args -> {
            //test write to CPS writing to kafka
            kafkaTemplate.send("integration_tanda_cps",new GwRequest(UUID.randomUUID(),1000f,"254708053872"));
        };
    }
}
