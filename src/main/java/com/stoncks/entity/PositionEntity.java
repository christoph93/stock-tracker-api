package com.stoncks.entity;

import com.stoncks.document.TransactionDocument;

import java.util.List;

public class PositionEntity {


    private String symbol, portfolioId;
    public double avgBuyPrice, avgSellPrice, unitsBought, unitsSold, totalPositionBought, totalPositionSold;
    List<TransactionDocument> transactionDocuments;

    public PositionEntity(String symbol, String portfolioId) {
        this.symbol = symbol;
        this.portfolioId = portfolioId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public double getUnitsBought() {
        return unitsBought;
    }

    public void setUnitsBought(double unitsBought) {
        this.unitsBought = unitsBought;
    }

    public double getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(double unitsSold) {
        this.unitsSold = unitsSold;
    }

    public double getTotalPositionBought() {
        return totalPositionBought;
    }

    public void setTotalPositionBought(double totalPositionBought) {
        this.totalPositionBought = totalPositionBought;
    }

    public double getTotalPositionSold() {
        return totalPositionSold;
    }

    public void setTotalPositionSold(double totalPositionSold) {
        this.totalPositionSold = totalPositionSold;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(double avgBuyPrice) {
        this.avgBuyPrice = avgBuyPrice;
    }

    public double getAvgSellPrice() {
        return avgSellPrice;
    }

    public void setAvgSellPrice(double avgSellPrice) {
        this.avgSellPrice = avgSellPrice;
    }


    public List<TransactionDocument> getTransactionDocuments() {
        return transactionDocuments;
    }

    public void setTransactionDocuments(List<TransactionDocument> transactionDocuments) {
        this.transactionDocuments = transactionDocuments;
    }
}
