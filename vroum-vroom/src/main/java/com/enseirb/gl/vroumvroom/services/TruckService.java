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
     * Redirect to a map web service depending on the last known position of a truck
     * @param id the id of the truck
     */
    public ResponseEntity<Object> getRedirectionToMap(int id) throws URISyntaxException {
        Position lastPosition = truckRepo.getTruckLastPosition(id);
        URI mapServiceLink = new URI(BreisenWebService.sendRequestToBreisenWebService("/map", lastPosition, String.class));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(mapServiceLink);
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}
