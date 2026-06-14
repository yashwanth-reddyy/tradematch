package com.tradematch.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TradeEventListener {

    @Async
    @EventListener
    public void handleTradeExecutionMessage(TradeEvent event) {
        System.out.println("[MESSAGE BROKER SIMULATOR] Asynchronously consumed event -> "
                + event.getQuantity() + " shares of " + event.getSymbol() + " processed @ $" + event.getPrice());
    }
}