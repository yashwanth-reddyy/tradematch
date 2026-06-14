package com.tradematch.controller;

import com.tradematch.model.Order;
import com.tradematch.service.OrderMatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderMatchingService matchingService;

    public OrderController(OrderMatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @PostMapping("/buy")
    public ResponseEntity<String> placeBuyOrder(@RequestBody OrderRequest request) {
        Order buyOrder = new Order(UUID.randomUUID().toString(), request.getSymbol(), "BUY", request.getPrice(), request.getQuantity());
        matchingService.processOrder(buyOrder);
        return ResponseEntity.ok("Buy Order Received for " + request.getQuantity() + " shares of " + request.getSymbol());
    }

    @PostMapping("/sell")
    public ResponseEntity<String> placeSellOrder(@RequestBody OrderRequest request) {
        Order sellOrder = new Order(UUID.randomUUID().toString(), request.getSymbol(), "SELL", request.getPrice(), request.getQuantity());
        matchingService.processOrder(sellOrder);
        return ResponseEntity.ok("Sell Order Received for " + request.getQuantity() + " shares of " + request.getSymbol());
    }

    @GetMapping("/analytics/{symbol}/average-price")
    public ResponseEntity<String> getAveragePrice(@PathVariable String symbol) {
        Double avgPrice = matchingService.getAveragePrice(symbol);
        if (avgPrice == null) return ResponseEntity.ok("No trades executed yet for " + symbol);
        return ResponseEntity.ok("Average Execution Price for " + symbol + " is $" + Math.round(avgPrice * 100.0) / 100.0);
    }
}

class OrderRequest {
    private String symbol;
    private double price;
    private int quantity;

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}