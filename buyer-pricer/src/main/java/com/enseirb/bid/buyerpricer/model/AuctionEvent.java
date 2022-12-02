package com.enseirb.bid.buyerpricer.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class AuctionEvent {
    private String type;
    private UUID auctionId;
    private Instant ts;
    private int bidderId;
    private BigDecimal bid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(UUID auctionId) {
        this.auctionId = auctionId;
    }

    public Instant getTs() {
        return ts;
    }

    public void setTs(Instant ts) {
        this.ts = ts;
    }

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "AuctionEvent{" +
                "type='" + type + '\'' +
                ", ts=" + ts +
                ", id=" + auctionId +
                ", bidderId=" + bidderId +
                ", bid=" + bid +
                '}';
    }
}
