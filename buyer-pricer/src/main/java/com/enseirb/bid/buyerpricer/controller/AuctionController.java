package com.enseirb.bid.buyerpricer.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auction")
public class AuctionController {

    @GetMapping("/{auctionId}")
    public String getAuctionInfo(@PathVariable UUID auctionId) {
        return "";
    }
}
