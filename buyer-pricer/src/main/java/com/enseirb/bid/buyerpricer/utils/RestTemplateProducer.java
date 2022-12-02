package com.enseirb.bid.buyerpricer.utils;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateProducer {
    private static final String BREISEN_URL = "http://breisen.datamix.ovh:8081";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri(BREISEN_URL).build();
    }
}
