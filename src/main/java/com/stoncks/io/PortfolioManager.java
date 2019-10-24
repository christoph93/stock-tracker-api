package com.stoncks.io;

import com.stoncks.document.Portfolio;
import com.stoncks.repository.PortfolioRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PortfolioManager {



    private PortfolioRepository portfolioRepository;

    public PortfolioManager(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }


    public String updateName(String name, String owner, String newName){
        Portfolio portfolio = portfolioRepository.findByNameAndOwner(name, owner);
        portfolio.setName(newName);
        return (portfolioRepository.save(portfolio) != null ? portfolio.getName() : null);
    }

    public String updateOwner(String name, String owner, String newOwner){
        Portfolio portfolio = portfolioRepository.findByNameAndOwner(name, owner);
        portfolio.setName(newOwner);
        return (portfolioRepository.save(portfolio) != null ? portfolio.getOwner() : null);
    }

    public String[] addSymbol(String name, String owner, String symbol){
        Portfolio portfolio = portfolioRepository.findByNameAndOwner(name, owner);
        HashSet<String> tempSymbols = new HashSet<>(Arrays.asList(portfolio.getSymbols()));
        if(!tempSymbols.add(symbol)) return null;
        Object [] aux = tempSymbols.toArray();
        String[] symbols = new String[tempSymbols.size()];
        //convert List to String[]
        for(int i = 0; i < tempSymbols.size(); i++){
            symbols[i] = aux[i].toString();
        }
        portfolio.setSymbols(symbols);
        return (portfolioRepository.save(portfolio) != null ? portfolio.getSymbols() : null);
    }


    public  String[] addSymbols(String name, String owner, List<String> symbolsList){
        Portfolio portfolio = portfolioRepository.findByNameAndOwner(name, owner);
        HashSet<String> tempSymbols = new HashSet<>(Arrays.asList(portfolio.getSymbols()));
        tempSymbols.addAll(Arrays.asList(portfolio.getSymbols()));
        tempSymbols.addAll(symbolsList);
        //convert List to String[]
        Object [] aux = tempSymbols.toArray();
        String[] symbols = new String[tempSymbols.size()];
        //convert List to String[]
        for(int i = 0; i < tempSymbols.size(); i++){
            symbols[i] = aux[i].toString();
        }
        portfolio.setSymbols(symbols);
        return (portfolioRepository.save(portfolio) != null ? portfolio.getSymbols() : null);
    }

    public String[] removeSymbol(String name, String owner, String symbol){
        Portfolio portfolio = portfolioRepository.findByNameAndOwner(name, owner);
        HashSet<String> tempSymbols = new HashSet<>(Arrays.asList(portfolio.getSymbols()));
        tempSymbols.remove(symbol);
        Object [] aux = tempSymbols.toArray();
        String[] symbols = new String[tempSymbols.size()];
        //convert List to String[]
        for(int i = 0; i < tempSymbols.size(); i++){
            symbols[i] = aux[i].toString();
        }
        portfolio.setSymbols(symbols);
        return (portfolioRepository.save(portfolio) != null ? portfolio.getSymbols() : null);
    }

    public String[] removeSymbols(String name, String owner, List<String> symbols){
        Portfolio portfolio = portfolioRepository.findByNameAndOwner(name, owner);
        HashSet<String> tempSymbols = new HashSet<>();
        tempSymbols.addAll(Arrays.asList(portfolio.getSymbols()));
        tempSymbols.removeAll(symbols);
        portfolio.setSymbols((String[]) tempSymbols.toArray());
        return (portfolioRepository.save(portfolio) != null ? portfolio.getSymbols() : null);
    }

    public boolean removeAllSymbols(String name, String owner){
        Portfolio portfolio = portfolioRepository.findByNameAndOwner(name, owner);
        portfolio.setSymbols(new String[] {});
        return (portfolioRepository.save(portfolio) != null);

    }

}
