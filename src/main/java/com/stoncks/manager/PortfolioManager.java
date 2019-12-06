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


    public String updateName(String name, String owner, String newName){
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner).get();
        portfolioDocument.setName(newName);
        return (portfolioRepository.save(portfolioDocument) != null ? portfolioDocument.getName() : null);
    }

    public String updateOwner(String name, String owner, String newOwner){
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner).get();
        portfolioDocument.setName(newOwner);
        return (portfolioRepository.save(portfolioDocument) != null ? portfolioDocument.getOwner() : null);
    }

    public String[] addSymbol(String name, String owner, String symbol){
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner).get();
        HashSet<String> tempSymbols = new HashSet<>(Arrays.asList(portfolioDocument.getSymbols()));
        if(!tempSymbols.add(symbol)) return null;
        Object [] aux = tempSymbols.toArray();
        String[] symbols = new String[tempSymbols.size()];
        //convert List to String[]
        for(int i = 0; i < tempSymbols.size(); i++){
            symbols[i] = aux[i].toString();
        }
        portfolioDocument.setSymbols(symbols);
        return (portfolioRepository.save(portfolioDocument) != null ? portfolioDocument.getSymbols() : null);
    }


    public  String[] addSymbols(String name, String owner, List<String> symbolsList){
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner).get();
        HashSet<String> tempSymbols = new HashSet<>(Arrays.asList(portfolioDocument.getSymbols()));
        tempSymbols.addAll(Arrays.asList(portfolioDocument.getSymbols()));
        tempSymbols.addAll(symbolsList);
        //convert List to String[]
        Object [] aux = tempSymbols.toArray();
        String[] symbols = new String[tempSymbols.size()];
        //convert List to String[]
        for(int i = 0; i < tempSymbols.size(); i++){
            symbols[i] = aux[i].toString();
        }
        portfolioDocument.setSymbols(symbols);
        return (portfolioRepository.save(portfolioDocument) != null ? portfolioDocument.getSymbols() : null);
    }

    public String[] removeSymbol(String name, String owner, String symbol){
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner).get();
        HashSet<String> tempSymbols = new HashSet<>(Arrays.asList(portfolioDocument.getSymbols()));
        tempSymbols.remove(symbol);
        Object [] aux = tempSymbols.toArray();
        String[] symbols = new String[tempSymbols.size()];
        //convert List to String[]
        for(int i = 0; i < tempSymbols.size(); i++){
            symbols[i] = aux[i].toString();
        }
        portfolioDocument.setSymbols(symbols);
        return (portfolioRepository.save(portfolioDocument) != null ? portfolioDocument.getSymbols() : null);
    }

    public String[] removeSymbols(String name, String owner, List<String> symbols){
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner).get();
        HashSet<String> tempSymbols = new HashSet<>();
        tempSymbols.addAll(Arrays.asList(portfolioDocument.getSymbols()));
        tempSymbols.removeAll(symbols);
        portfolioDocument.setSymbols((String[]) tempSymbols.toArray());
        return (portfolioRepository.save(portfolioDocument) != null ? portfolioDocument.getSymbols() : null);
    }

    public boolean removeAllSymbols(String name, String owner){
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner).get();
        portfolioDocument.setSymbols(new String[] {});
        return (portfolioRepository.save(portfolioDocument) != null);

    }

}
