package com.enseirb.bid.buyerpricer.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Auction {
    private UUID id;
    private Boolean expired;
    private Instant startDate;
    private Duration duration;
    private int sellerId;
    private int bidderId;
    private BigDecimal bid;

    public Auction(AuctionEvent event, AuctionInfo info) {
        this.id = event.getAuctionId();
        this.bidderId = event.getBidderId();
        this.bid = event.getBid();
        this.startDate = info.getStartDate();
        this.sellerId = info.getSellerId();
        this.duration = info.getDuration();
        this.expired = false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
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
}
