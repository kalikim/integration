package com.tanda.paymentgateway.integration.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {
    @Bean
    public NewTopic topic1Builder() {
        return TopicBuilder.name("integration_tanda_cps").build();
    }
    @Bean
    public NewTopic topicBuilder(){
        return TopicBuilder.name("integration_tanda_cps_callback").build();
    }
}
