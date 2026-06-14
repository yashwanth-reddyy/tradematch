package com.tradematch.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "executed_trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private double executionPrice;
    private int quantity;
    private LocalDateTime executionTime;

    public Trade() {}

    public Trade(String symbol, double executionPrice, int quantity) {
        this.symbol = symbol;
        this.executionPrice = executionPrice;
        this.quantity = quantity;
        this.executionTime = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getSymbol() { return symbol; }
    public double getExecutionPrice() { return executionPrice; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getExecutionTime() { return executionTime; }
}