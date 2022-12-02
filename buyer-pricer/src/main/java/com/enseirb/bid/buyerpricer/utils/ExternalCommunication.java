package com.enseirb.bid.buyerpricer.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalCommunication {

    @Autowired RestTemplate restTemplate;

    public <T> T sendGetRequest(String endpoint, Class<T> responseType, Object... uriVariables) {
        return restTemplate.getForObject(endpoint, responseType, uriVariables);
    }
}
