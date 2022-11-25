package com.enseirb.gl.vroumvroom.utils;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateProducer {
    private final static String BREINSEN_URL = "http://breisen.datamix.ovh:8080";

    /**
     * Create a rest template using Breisen_URL as it's root Uri
     * @param builder a rest template builder
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri(BREINSEN_URL).build();
    }
}
