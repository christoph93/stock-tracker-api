package com.stoncks.manager;

import com.stoncks.document.PortfolioDocument;
import com.stoncks.entity.PortfolioEntity;
import com.stoncks.repository.PortfolioRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class PortfolioManager {

    private PortfolioRepository portfolioRepository;

    public PortfolioManager(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }


    public PortfolioEntity portfolioEntityFromDocument(String id){
        Optional opt;
        opt = portfolioRepository.findById(id);

        if(!opt.isPresent()) return null;
        PortfolioDocument portfolioDocument = (PortfolioDocument) opt.get();

        PortfolioEntity portfolioEntity = new PortfolioEntity(
                portfolioDocument.getSymbols(),
                portfolioDocument.getOwner(),
                portfolioDocument.getName()
        )


    }


    public String updateName(String name, String owner, String newName){
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner);
        portfolioDocument.setName(newName);
        return (portfolioRepository.save(portfolioDocument) != null ? portfolioDocument.getName() : null);
    }

    public String updateOwner(String name, String owner, String newOwner){
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner);
        portfolioDocument.setName(newOwner);
        return (portfolioRepository.save(portfolioDocument) != null ? portfolioDocument.getOwner() : null);
    }

    public String[] addSymbol(String name, String owner, String symbol){
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner);
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
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner);
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
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner);
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
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner);
        HashSet<String> tempSymbols = new HashSet<>();
        tempSymbols.addAll(Arrays.asList(portfolioDocument.getSymbols()));
        tempSymbols.removeAll(symbols);
        portfolioDocument.setSymbols((String[]) tempSymbols.toArray());
        return (portfolioRepository.save(portfolioDocument) != null ? portfolioDocument.getSymbols() : null);
    }

    public boolean removeAllSymbols(String name, String owner){
        PortfolioDocument portfolioDocument = portfolioRepository.findByNameAndOwner(name, owner);
        portfolioDocument.setSymbols(new String[] {});
        return (portfolioRepository.save(portfolioDocument) != null);

    }

}
