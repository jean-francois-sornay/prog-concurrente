package com.enseirb.gl.vroumvroom;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/truck")
public class TruckController {

    @Autowired TruckRepository truckRepo;

    private final static String BREINSEN_URL = "http://breisen.datamix.ovh:8080/map";

    @GetMapping("")
    public String getTruck() {
        return new Truck().toString();
    }

    @GetMapping("/{id}")
    public Truck getTruckById(@PathVariable int id) {
        return truckRepo.getLastPosition(id);
    }


    @RequestMapping("/{id}/map")
    public ResponseEntity<Object> redirectToExternalUrl(@PathVariable int id) throws URISyntaxException {
        URI breisenUrl = new URI(sendRequestToWebService(id));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(breisenUrl);
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    private String sendRequestToWebService(int id) {

        RestTemplate restTemplate = new RestTemplate();
        Position t = truckRepo.getLastPosition(id).getPosition();

        return restTemplate.postForObject(BREINSEN_URL, t, String.class);
    }
}
