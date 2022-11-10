package com.enseirb.gl.vroumvroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/truck")
public class TruckController {

    @Autowired TruckRepository truckRepo;

    @GetMapping("")
    public String getTruck() {
        return new Truck().toString();
    }

    /*@GetMapping("/{id}")
    public Truck getTruckById(@PathVariable int id) {
        return truckRepo.getById(id);
    }*/
}
