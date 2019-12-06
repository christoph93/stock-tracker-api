package com.stoncks.manager;

import com.stoncks.document.PortfolioDocument;
import com.stoncks.document.SymbolDocument;
import com.stoncks.document.TransactionDocument;
import com.stoncks.entity.PortfolioEntity;
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


    public PortfolioEntity portfolioEntityFromDocument(String owner, String name){
        AtomicReference<PortfolioEntity> pe = null;
        portfolioRepository.findByNameAndOwner(owner, name).ifPresent(portfolioDocument -> pe.set(portfolioEntityFromDocument(portfolioDocument.getId())));
        return null;
        }

    public PortfolioEntity portfolioEntityFromDocument(String id){
        AtomicReference<PortfolioDocument> pd = null;
        portfolioRepository.findById(id).ifPresent(pd::set);

        PortfolioEntity portfolioEntity = new PortfolioEntity(
                pd.get().getId(),
                pd.get().getSymbols(),
                pd.get().getOwner(),
                pd.get().getName()
        );

        ArrayList<SymbolDocument> symbolDocuments = new ArrayList<>(portfolioEntity.getSymbols().length);
        ArrayList<TransactionDocument> transactionDocuments = new ArrayList<>();

        for(String s : portfolioEntity.getSymbols()){
            symbolRepository.findBySymbol(s).ifPresent(symbolDocuments::add);
            transactionRepository.findBySymbol(s).ifPresent(transactionDocuments::addAll);
        }

        portfolioEntity.setSymbolDocuments(symbolDocuments);
        portfolioEntity.setTransactionDocuments(transactionDocuments);

        return portfolioEntity;
    }


    public String[] addSymbol(PortfolioEntity portfolioEntity, String symbol){
        //get the document
        PortfolioDocument portfolioDocument = portfolioRepository.findById(portfolioEntity.getId()).get();
        HashSet<String> tempSymbols = new HashSet<>(Arrays.asList(portfolioEntity.getSymbols()));

        tempSymbols.add(symbol);

        String[] symbolsArray = (String[]) tempSymbols.toArray();

        portfolioDocument.setSymbols(symbolsArray);
        portfolioEntity.setSymbols(symbolsArray);

        portfolioRepository.save(portfolioDocument);

        return symbolsArray;
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
