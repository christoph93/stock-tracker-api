package com.stoncks.entity;

import com.stoncks.document.Transaction;

import java.util.List;

public class Position {


    private String symbol, portfolioId;
    public double avgBuyPrice, avgSellPrice, unitsBought, unitsSold, totalPositionBought, totalPositionSold, netProfit, profitPercent,openPositionValue, openPosition;
    List<Transaction> transactions;
    public String state;

    public Position(String symbol, String portfolioId) {
        this.symbol = symbol;
        this.portfolioId = portfolioId;
    }

    public double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(double netProfit) {
        this.netProfit = netProfit;
    }

    public double getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(double profitPercent) {
        this.profitPercent = profitPercent;
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


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Position{" +
                "symbol='" + symbol + '\'' +
                ", portfolioId='" + portfolioId + '\'' +
                ", avgBuyPrice=" + avgBuyPrice +
                ", avgSellPrice=" + avgSellPrice +
                ", unitsBought=" + unitsBought +
                ", unitsSold=" + unitsSold +
                ", totalPositionBought=" + totalPositionBought +
                ", totalPositionSold=" + totalPositionSold +
                ", netProfit=" + netProfit +
                ", profitPercent=" + profitPercent +
                ", openPositionValue=" + openPositionValue +
                ", openPosition=" + openPosition +
                ", state='" + state + '\'' +
                '}';
    }
}
