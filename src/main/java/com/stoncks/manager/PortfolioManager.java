
package com.stoncks.manager;

import com.stoncks.document.Portfolio;
import com.stoncks.document.Symbol;
import com.stoncks.document.Transaction;
import com.stoncks.entity.Position;
import com.stoncks.repository.PortfolioRepository;
import com.stoncks.repository.SymbolRepository;
import com.stoncks.repository.TransactionRepository;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class PortfolioManager {

    private PortfolioRepository portfolioRepository;
    private SymbolRepository symbolRepository;
    private TransactionRepository transactionRepository;

    public PortfolioManager(PortfolioRepository portfolioRepository, SymbolRepository symbolRepository, TransactionRepository transactionRepository) {
        this.portfolioRepository = portfolioRepository;
        this.symbolRepository = symbolRepository;
        this.transactionRepository = transactionRepository;
    }


    public Portfolio createPortfolio(String owner, String name){
        //check if portfolio already exists or create a new one
        Portfolio portfolio;
        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByOwnerAndName(owner, name);

        if(optionalPortfolio.isPresent()){
            return optionalPortfolio.get();
        } else {
            portfolio = new Portfolio(owner, name);
        }

        portfolioRepository.save(portfolio);
        return portfolioRepository.findByOwnerAndName(owner, name).get();
    }


    public boolean addSymbol(Portfolio portfolio, String symbol){
        Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolio.getId());

        if(optionalPortfolio.isPresent()){
            optionalPortfolio.get().addSymbol(symbol);
            portfolioRepository.save(optionalPortfolio.get());
            return true;
        }
        return false;
    }


    public boolean addSymbols(Portfolio portfolio, List<String> symbols){
        Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolio.getId());

        if(optionalPortfolio.isPresent()){
            optionalPortfolio.get().addSymbols(symbols);
            portfolioRepository.save(optionalPortfolio.get());
            return true;
        }
        return false;
    }

    public boolean removeSymbol(Portfolio portfolio, String symbol){
        Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolio.getId());

        if(optionalPortfolio.isPresent()){
            optionalPortfolio.get().removeSymbol(symbol);
            portfolioRepository.save(optionalPortfolio.get());
            return true;
        }
        return false;
    }

    public boolean removeSymbols(Portfolio portfolio, List<String> symbols){
        Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolio.getId());

        if(optionalPortfolio.isPresent()){
            optionalPortfolio.get().removeSymbols(symbols);
            portfolioRepository.save(optionalPortfolio.get());
            return true;
        }
        return false;
    }


    public void generatePositions (Portfolio portfolio){
        ArrayList<Symbol> symbols = new ArrayList<>();
        for(String s : portfolio.getSymbols()) {
            System.out.println("finding symbol" + s);
            symbolRepository.findBySymbol(s).ifPresent(symbols::add);
        }

        ArrayList<Position> positions = new ArrayList<>();

        for(Symbol symbol : symbols){
            //create a new position for each symbol
            positions.add(new Position(symbol.getSymbol(), portfolio.getId()));
        }

        portfolio.setPositions(positions);


        //load transactions for each position
        for(Position position : portfolio.getPositions()) {
            transactionRepository.findBySymbol(position.getSymbol().replace(".SAO", "") ).ifPresent(position::setTransactions);

            //calculate avgs and profits from transactions
            for(Transaction transaction : position.getTransactions()){
                if(transaction.getOperation().equals("C")){
                    position.unitsBought+= transaction.getQuantity();
                    position.totalPositionBought+=transaction.getTotalPrice();
                } else if (transaction.getOperation().equals("V")){
                    position.unitsSold+=transaction.getQuantity();
                    position.totalPositionSold+=transaction.getTotalPrice();
                }
            }

            position.avgBuyPrice = position.totalPositionBought/position.unitsBought;

            if(position.unitsSold != 0){
                position.avgSellPrice = position.totalPositionSold/position.unitsSold;
            } else {
                position.avgSellPrice = 0;
            }

            position.openPosition = position.unitsBought - position.unitsSold;

            Symbol tempSymbol = symbolRepository.findBySymbol(position.getSymbol()).get();

            position.openPositionValue = (position.openPosition*tempSymbol.getLastPrice()) -(position.openPosition*position.avgBuyPrice);



        }


    }





}
