package com.stoncks.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class SymbolDocument {

    @Id
    private String id;

    private Object fullContent;
    private Object closingPrices;
    private Date createDate;
    private String symbol;
    private double lastPrice;
    private Date lastPriceDate;

    public SymbolDocument(Object fullContent, Date createDate, String symbol) {
        this.fullContent = fullContent;
        this.createDate = createDate;
        this.symbol = symbol;
    }

    public String getId() {
        return id;
    }

    public Object getClosingPrices() {
        return closingPrices;
    }

    public void setClosingPrices(Object closingPrices) {
        this.closingPrices = closingPrices;
    }

    public Object getFullContent() {
        return fullContent;
    }

    public void setFullContent(Object fullContent) {
        this.fullContent = fullContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "id='" + id + '\'' +
                ", content='" + fullContent + '\'' +
                ", createDate=" + createDate +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
