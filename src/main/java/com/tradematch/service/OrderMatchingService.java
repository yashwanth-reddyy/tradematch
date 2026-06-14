package com.tradematch.service;

import com.tradematch.model.Order;
import com.tradematch.model.Trade;
import com.tradematch.event.TradeEvent;
import com.tradematch.repository.TradeRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;

@Service
public class OrderMatchingService {

    private final TradeRepository tradeRepository;
    private final ApplicationEventPublisher eventPublisher;
    private PriorityQueue<Order> buyOrders;
    private PriorityQueue<Order> sellOrders;

    public OrderMatchingService(TradeRepository tradeRepository, ApplicationEventPublisher eventPublisher) {
        this.tradeRepository = tradeRepository;
        this.eventPublisher = eventPublisher;

        buyOrders = new PriorityQueue<>((a, b) -> {
            if (Double.compare(b.getPrice(), a.getPrice()) == 0) {
                return Long.compare(a.getTimestamp(), b.getTimestamp());
            }
            return Double.compare(b.getPrice(), a.getPrice());
        });

        sellOrders = new PriorityQueue<>((a, b) -> {
            if (Double.compare(a.getPrice(), b.getPrice()) == 0) {
                return Long.compare(a.getTimestamp(), b.getTimestamp());
            }
            return Double.compare(a.getPrice(), b.getPrice());
        });
    }

    public void processOrder(Order newOrder) {
        if (newOrder.getSide().equalsIgnoreCase("BUY")) {
            matchBuyOrder(newOrder);
        } else {
            matchSellOrder(newOrder);
        }
    }

    private void matchBuyOrder(Order buyOrder) {
        while (!sellOrders.isEmpty() && buyOrder.getPrice() >= sellOrders.peek().getPrice() && buyOrder.getQuantity() > 0) {
            Order bestSell = sellOrders.poll();
            int tradedQuantity = Math.min(buyOrder.getQuantity(), bestSell.getQuantity());

            executeTrade(buyOrder.getSymbol(), bestSell.getPrice(), tradedQuantity);

            buyOrder.setQuantity(buyOrder.getQuantity() - tradedQuantity);
            bestSell.setQuantity(bestSell.getQuantity() - tradedQuantity);

            if (bestSell.getQuantity() > 0) {
                sellOrders.add(bestSell);
            }
        }
        if (buyOrder.getQuantity() > 0) {
            buyOrders.add(buyOrder);
        }
    }

    private void matchSellOrder(Order sellOrder) {
        while (!buyOrders.isEmpty() && sellOrder.getPrice() <= buyOrders.peek().getPrice() && sellOrder.getQuantity() > 0) {
            Order bestBuy = buyOrders.poll();
            int tradedQuantity = Math.min(sellOrder.getQuantity(), bestBuy.getQuantity());

            executeTrade(sellOrder.getSymbol(), bestBuy.getPrice(), tradedQuantity);

            sellOrder.setQuantity(sellOrder.getQuantity() - tradedQuantity);
            bestBuy.setQuantity(bestBuy.getQuantity() - tradedQuantity);

            if (bestBuy.getQuantity() > 0) {
                buyOrders.add(bestBuy);
            }
        }
        if (sellOrder.getQuantity() > 0) {
            sellOrders.add(sellOrder);
        }
    }

    private void executeTrade(String symbol, double price, int quantity) {
        System.out.println("SUCCESS! TRADE EXECUTED: " + quantity + " shares of " + symbol + " @ $" + price);

        // 1. Persist using Hibernate
        Trade trade = new Trade(symbol, price, quantity);
        tradeRepository.save(trade);

        // 2. Stream using Event Publisher (Kafka simulation)
        eventPublisher.publishEvent(new TradeEvent(symbol, price, quantity));
    }

    public Double getAveragePrice(String symbol) {
        return tradeRepository.getAverageExecutionPrice(symbol);
    }
}