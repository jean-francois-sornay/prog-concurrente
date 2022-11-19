package com.enseirb.gl.vroumvroom.controllers;


import com.enseirb.gl.vroumvroom.models.Position;
import com.enseirb.gl.vroumvroom.models.Truck;
import com.enseirb.gl.vroumvroom.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/truck")
public class TruckController {
    @Autowired
    private TruckService truckService;


    /**
     * Get the list of all trucks known
     */
    @GetMapping("")
    public List<Truck> listTrucks() {
        return truckService.getTrucks();
    }


    /**
     * Get the last position of a truck
     * @param id the id of the truck
     */
    @GetMapping("/{id}")
    public Position getLastPosition(@PathVariable int id) {
        return truckService.getLastPosition(id);
    }


    /**
     * Redirect to a map web service depending on the last known position of a truck
     * @param id the id of the truck
     */
    @RequestMapping("/{id}/map")
    public ResponseEntity<Object> redirectToMap(@PathVariable int id) throws URISyntaxException {
        return truckService.getRedirectionToMap(id);
    }
}
