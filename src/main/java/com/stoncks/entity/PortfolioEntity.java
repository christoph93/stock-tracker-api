package com.stoncks.entity;

import com.stoncks.document.TickerDocument;
import com.stoncks.document.TransactionDocument;

import java.util.ArrayList;
import java.util.Arrays;

public class PortfolioEntity {


    private ArrayList<TransactionDocument> transactionDocuments;
    private ArrayList<TickerDocument> symbolDocuments;
    private String[] symbols;

    private String name, owner;

    public PortfolioEntity(String[] symbols, String name, String owner) {
        this.symbols = symbols;
        this.name = name;
        this.owner = owner;
    }

    public ArrayList<TransactionDocument> getTransactionDocuments() {
        return transactionDocuments;
    }

    public void setTransactionDocuments(ArrayList<TransactionDocument> transactionDocuments) {
        this.transactionDocuments = transactionDocuments;
    }

    public ArrayList<TickerDocument> getSymbolDocuments() {
        return symbolDocuments;
    }

    public void setSymbolDocuments(ArrayList<TickerDocument> symbolDocuments) {
        this.symbolDocuments = symbolDocuments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
