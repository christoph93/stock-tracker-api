package com.stoncks.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
import java.util.UUID;

@Document
public class Transaction {


    @Id
    private UUID transactionID;
    private Date date;
    private String operation;
    private String code;
    private String description;
    private double quantity;
    private double price;
    private double totalPrice;


    public Transaction(Date date, String operation, String code, String description, double quantity, double price) {
        this.date = date;
        this.operation = operation;
        this.code = code;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.transactionID = UUID.randomUUID();
    }

    public UUID getTransactionID() {
        return transactionID;
    }


    public void setTransactionID(UUID transactionID) {
        this.transactionID = transactionID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        return "transaction{" +
                "transactionID=" + transactionID.toString() +
                ", date=" + date +
                ", operation='" + operation + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
