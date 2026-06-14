package com.tradematch.model;

public class Order {
    private String orderId;
    private String symbol;
    private String side;
    private double price;
    private int quantity;
    private long timestamp;

    public Order(String orderId, String symbol, String side, double price, int quantity) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = System.nanoTime();
    }

    public String getOrderId() { return orderId; }
    public String getSymbol() { return symbol; }
    public String getSide() { return side; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public long getTimestamp() { return timestamp; }
}