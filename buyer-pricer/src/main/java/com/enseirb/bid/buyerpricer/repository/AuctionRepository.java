package com.enseirb.bid.buyerpricer.repository;

import com.enseirb.bid.buyerpricer.model.Auction;
import com.enseirb.bid.buyerpricer.model.AuctionEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuctionRepository {
    private final ConcurrentHashMap<UUID, Auction> auctions = new ConcurrentHashMap<>();

    public synchronized void storeAuction(Auction auction) {
        auctions.put(auction.getId(), auction);
    }

    public synchronized Auction getAuction(UUID id) {
        return auctions.get(id);
    }

    /*
    public synchronized void updateAuction(AuctionEvent event) {
        Auction auction = auctions.get(event.getId());
        auction.setExpired(event.getType().equals("expired"));
        auction.setBidderId(event.getBidderId());
        auction.setBid(event.getBid());
        auction.setDuration(event.getDuration());
    }*/
}
