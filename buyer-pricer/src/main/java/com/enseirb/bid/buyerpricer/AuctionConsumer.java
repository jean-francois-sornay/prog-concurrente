package com.enseirb.bid.buyerpricer;

import com.enseirb.bid.buyerpricer.model.Auction;
import com.enseirb.bid.buyerpricer.model.AuctionEvent;
import com.enseirb.bid.buyerpricer.model.AuctionInfo;
import com.enseirb.bid.buyerpricer.repository.AuctionRepository;
import com.enseirb.bid.buyerpricer.utils.ExternalCommunication;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuctionConsumer implements ConsumerSeekAware {
    private final static String TOPIC = "auction.event";
    private final static String GROUP_ID = "";
    private final Logger logger = LoggerFactory.getLogger(AuctionConsumer.class);
    @Autowired private AuctionRepository repository;
    @Autowired private ExternalCommunication externalCommunication;

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        callback.seekToEnd(assignments.keySet());
    }

    @KafkaListener(id = "5", topics = TOPIC)
    public void listen(AuctionEvent event) {
        logger.info("Received event: {}", event);

        Auction auction = repository.getAuction(event.getAuctionId());
        if (auction == null) {
            if (event.getType().equals("START")) {
                AuctionInfo info = externalCommunication.sendGetRequest("/auction/{autionId}", AuctionInfo.class, event.getAuctionId());
                repository.storeAuction(new Auction(event, info));
            }
        }
        else if (auction.getExpired()) {
            return;
        }
        else if (event.getType().equals("FINISH")) {
            auction.setExpired(true);
        }
        else if (event.getType().equals("BID")) {
            if (auction.getBid() == null || auction.getBid().compareTo(event.getBid()) < 0) {
                auction.setBidderId(event.getBidderId());
                auction.setBid(event.getBid());
            }
            else {
                logger.error("Bid too low: {}", event);
            }
        }
    }
}
