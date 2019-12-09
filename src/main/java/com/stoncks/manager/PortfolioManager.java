
package com.stoncks.manager;

import com.stoncks.document.PortfolioDocument;
import com.stoncks.document.SymbolDocument;
import com.stoncks.document.TransactionDocument;
import com.stoncks.entity.PortfolioEntity;
import com.stoncks.entity.PositionEntity;
import com.stoncks.repository.PortfolioRepository;
import com.stoncks.repository.SymbolRepository;
import com.stoncks.repository.TransactionRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class PortfolioManager {

    private PortfolioRepository portfolioRepository;
    private SymbolRepository symbolRepository;
    private TransactionRepository transactionRepository;

    public PortfolioManager(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }


    public void pullSymbols(PortfolioEntity portfolioEntity){
        HashSet<String> symbols = new HashSet<>(Arrays.asList(portfolioEntity.getSymbols()));

        for (String s : symbols){
            addSymbol(portfolioEntity, s);
        }

    }


    public void calculatePositions(PortfolioEntity portfolioEntity){
        pullSymbols(portfolioEntity);

        //reset positions
        portfolioEntity.setPositions(new ArrayList<>());
        for(SymbolDocument sd : portfolioEntity.getSymbolDocuments()){
            portfolioEntity.getPositions().add(new PositionEntity(sd.getSymbol(), portfolioEntity.getId()));
        }
        //load transactions for each position
        for(PositionEntity pe : portfolioEntity.getPositions()) {
            transactionRepository.findBySymbol(pe.getSymbol()).ifPresent(pe::setTransactionDocuments);

            //calculate avgs and profits from transactions
            for(TransactionDocument td : pe.getTransactionDocuments()){
                if(td.getOperation().equals("C")){
                    pe.unitsBought+= td.getQuantity();
                    pe.totalPositionBought+=td.getTotalPrice();
                } else if (td.getOperation().equals("V")){
                    pe.unitsSold+=td.getQuantity();
                    pe.totalPositionSold+=td.getTotalPrice();
                }
            }

            pe.avgBuyPrice = pe.totalPositionBought/pe.unitsBought;

            if(pe.unitsSold != 0){
                pe.avgSellPrice = pe.totalPositionSold/pe.unitsSold;
            } else {
                pe.avgSellPrice = 0;
            }
        }


    }

/*
    public Optional<PortfolioEntity> portfolioEntityFromDocument(String owner, String name){
        AtomicReference<PortfolioEntity> pe = null;
        portfolioRepository.findByNameAndOwner(owner, name).ifPresent(portfolioDocument -> pe.set(portfolioEntityFromDocument(portfolioDocument.getId())));
        return null;
        }

 */

    public PortfolioEntity portfolioEntityFromDocument(PortfolioDocument pd){

        PortfolioEntity portfolioEntity = new PortfolioEntity(
                pd.getId(),
                pd.getSymbols(),
                pd.getOwner(),
                pd.getName()
        );

        ArrayList<SymbolDocument> symbolDocuments = new ArrayList<>(portfolioEntity.getSymbols().length);

        for(String s : portfolioEntity.getSymbols()){
            symbolRepository.findBySymbol(s).ifPresent(symbolDocuments::add);
        }

        portfolioEntity.setSymbolDocuments(symbolDocuments);

        return portfolioEntity;
    }


    public void addSymbol(PortfolioEntity portfolioEntity, String symbol){
        //get the document
        PortfolioDocument portfolioDocument = portfolioRepository.findById(portfolioEntity.getId()).get();
        HashSet<String> tempSymbols = new HashSet<>(Arrays.asList(portfolioEntity.getSymbols()));

        tempSymbols.add(symbol);

        String[] symbolsArray = (String[]) tempSymbols.toArray();

        portfolioDocument.setSymbols(symbolsArray);
        portfolioEntity.setSymbols(symbolsArray);

        portfolioRepository.save(portfolioDocument);
    }


    public  String[] addSymbols(PortfolioEntity portfolioEntity, List<String> symbolsList){
        PortfolioDocument portfolioDocument = portfolioRepository.findById(portfolioEntity.getId()).get();
        HashSet<String> tempSymbols = new HashSet<>(Arrays.asList(portfolioEntity.getSymbols()));

        tempSymbols.addAll(symbolsList);

        String[] symbolsArray = (String[]) tempSymbols.toArray();

        portfolioDocument.setSymbols(symbolsArray);
        portfolioEntity.setSymbols(symbolsArray);

        portfolioRepository.save(portfolioDocument);

        return symbolsArray;
    }

    public String[] removeSymbol(PortfolioEntity portfolioEntity,  String symbol){
        //get the document
        PortfolioDocument portfolioDocument = portfolioRepository.findById(portfolioEntity.getId()).get();
        HashSet<String> tempSymbols = new HashSet<>(Arrays.asList(portfolioEntity.getSymbols()));

        tempSymbols.remove(symbol);

        String[] symbolsArray = (String[]) tempSymbols.toArray();

        portfolioDocument.setSymbols(symbolsArray);
        portfolioEntity.setSymbols(symbolsArray);

        portfolioRepository.save(portfolioDocument);

        return symbolsArray;
    }

    public String[] removeSymbols(PortfolioEntity portfolioEntity,  List<String> symbolsList){
        //get the document
        PortfolioDocument portfolioDocument = portfolioRepository.findById(portfolioEntity.getId()).get();
        HashSet<String> tempSymbols = new HashSet<>(Arrays.asList(portfolioEntity.getSymbols()));

        tempSymbols.removeAll(symbolsList);

        String[] symbolsArray = (String[]) tempSymbols.toArray();

        portfolioDocument.setSymbols(symbolsArray);
        portfolioEntity.setSymbols(symbolsArray);

        portfolioRepository.save(portfolioDocument);

        return symbolsArray;
    }

    public boolean removeAllSymbols(PortfolioEntity portfolioEntity){
        PortfolioDocument portfolioDocument = portfolioRepository.findById(portfolioEntity.getId()).get();
        String[] symbolsArray = new String[1];

        portfolioDocument.setSymbols(symbolsArray);
        portfolioEntity.setSymbols(symbolsArray);

        portfolioRepository.save(portfolioDocument);
        return true;
    }

}
