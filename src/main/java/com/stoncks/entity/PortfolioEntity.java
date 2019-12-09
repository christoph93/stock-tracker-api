package com.stoncks.entity;

import com.stoncks.document.PortfolioDocument;
import com.stoncks.document.SymbolDocument;

import java.util.ArrayList;

public class PortfolioEntity {


    private ArrayList<SymbolDocument> symbolDocuments;
    private String[] symbols;
    private ArrayList<PositionEntity> positions;

    private String name, owner, id;

    public PortfolioEntity(String id, String[] symbols, String name, String owner) {
        this.symbols = symbols;
        this.name = name;
        this.owner = owner;
        this.id = id;
    }

    public PortfolioEntity(String[] symbols, String name, String owner) {
        this.symbols = symbols;
        this.name = name;
        this.owner = owner;
    }

    public PortfolioDocument asDocument(){
        PortfolioDocument pe = new PortfolioDocument(this.name, this.owner);
        pe.setSymbols(this.symbols);
        pe.setId(this.id);
        return pe;
    }


    public ArrayList<PositionEntity> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<PositionEntity> positions) {
        this.positions = positions;
    }

    public String[] getSymbols() {
        return symbols;
    }

    public void setSymbols(String[] symbols) {
        this.symbols = symbols;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
