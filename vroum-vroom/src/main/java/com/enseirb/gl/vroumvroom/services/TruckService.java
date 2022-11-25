package com.enseirb.gl.vroumvroom.services;

import com.enseirb.gl.vroumvroom.models.Position;
import com.enseirb.gl.vroumvroom.models.Truck;
import com.enseirb.gl.vroumvroom.repositories.TruckRepository;
import com.enseirb.gl.vroumvroom.utils.BreisenWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class TruckService {
    @Autowired
    private TruckRepository truckRepo;

    @Autowired
    private BreisenWebService breisenWebService;


    /**
     * Get the list of all trucks known
     */
    public List<Truck> getTrucks() {
        return truckRepo.getTrucks();
    }


    /**
     * Get the last known position of a truck
     * @param id the id of the truck
     */
    public Position getLastPosition(int id) {
        return truckRepo.getTruckLastPosition(id);
    }


    /**
     * return the link to the map where the truck with the given id is
     * @param id the id of the truck
     */
    public String getBreisenMap(int id) {
        return breisenWebService.sendRequestToBreisenWebService("/map", getLastPosition(id), String.class);
    }
}
