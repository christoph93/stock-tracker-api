package com.stoncks.entity;

import com.stoncks.document.SymbolDocument;
import com.stoncks.document.TransactionDocument;

import java.util.ArrayList;

public class PortfolioEntity {


    private ArrayList<TransactionDocument> transactionDocuments;
    private ArrayList<SymbolDocument> symbolDocuments;
    private String[] symbols;

    private String name, owner;

    public PortfolioEntity(String[] symbols, String name, String owner) {
        this.symbols = symbols;
        this.name = name;
        this.owner = owner;
    }

    public String[] getSymbols() {
        return symbols;
    }

    public void setSymbols(String[] symbols) {
        this.symbols = symbols;
    }

    public ArrayList<TransactionDocument> getTransactionDocuments() {
        return transactionDocuments;
    }

    public void setTransactionDocuments(ArrayList<TransactionDocument> transactionDocuments) {
        this.transactionDocuments = transactionDocuments;
    }

    public ArrayList<SymbolDocument> getSymbolDocuments() {
        return symbolDocuments;
    }

    public void setSymbolDocuments(ArrayList<SymbolDocument> symbolDocuments) {
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
