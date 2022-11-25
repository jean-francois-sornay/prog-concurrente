package com.enseirb.gl.vroumvroom.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BreisenWebService {

    @Autowired
    RestTemplate restTemplate;

    /**
     * Send a request to the Breisen web service
     */
    public <T> T sendRequestToBreisenWebService(String endpoint, Object request, Class<T> responseType) {
        return restTemplate.postForObject(endpoint, request, responseType);
    }
}
