package com.enseirb.gl.vroumvroom.utils;

import org.springframework.web.client.RestTemplate;

public class BreisenWebService {
    private final static String BREINSEN_URL = "http://breisen.datamix.ovh:8080";


    /**
     * Send a request to the Breisen web service
     */
    public static <T> T sendRequestToBreisenWebService(String endpoint, Object request, Class<T> responseType) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(BREINSEN_URL + endpoint, request, responseType);
    }
}
