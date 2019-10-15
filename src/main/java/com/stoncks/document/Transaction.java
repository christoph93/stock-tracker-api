package com.stoncks.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document
public class Transaction {


    @Id
    private String id;
    private String date;
    private String operation;
    private String symbol;
    private String description;
    private double quantity;
    private double price;
    private double totalPrice;
    private long createDate;


    public Transaction(String date, String operation, String symbol, String description, double quantity, double price, double totalPrice, long createDate) {
        this.date = date;
        this.operation = operation;
        this.symbol = symbol;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", operation='" + operation + '\'' +
                ", code='" + symbol + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", createDate=" + createDate +
                '}';
    }
}
