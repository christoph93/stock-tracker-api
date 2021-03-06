package com.stocktrackerapi.service;




import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.StockTrackerApi;
import com.stocktrackerapi.document.Symbol;
import com.stocktrackerapi.document.Transaction;
import com.stocktrackerapi.io.PriceReader;
import com.stocktrackerapi.io.TickerFromUrl;
import com.stocktrackerapi.repository.SymbolRepository;
import com.stocktrackerapi.repository.TransactionRepository;

public class TickerUpdater implements Runnable {

    private TransactionRepository transactionRepository;
    private SymbolRepository symbolRepository;
    private int reqLimitPerMin;
    private boolean onlyMissingSymbols;

    public TickerUpdater(TransactionRepository transactionRepository, SymbolRepository symbolRepository, int reqLimitPerMin, boolean onlyMissingSymbols){
        this.symbolRepository = symbolRepository;
        this.transactionRepository = transactionRepository;
        this.reqLimitPerMin = reqLimitPerMin;
        this.onlyMissingSymbols = onlyMissingSymbols;
    }

    private String[] getMissingSymbolsFromTransactions(){
        String[] allSymbols = getAllSymbolsFromTransactions();
        List<Symbol> existingSymbolDocuments = symbolRepository.findAll();
        HashSet<String> existingSymbols = new HashSet<>();
        HashSet<String> missingSymbols = new HashSet<>();

        for(Symbol t : existingSymbolDocuments){
            existingSymbols.add(t.getSymbol());
        }

        for(String s : allSymbols){
            if(!existingSymbols.contains(s)){
                missingSymbols.add(s);
            }
        }
        return Arrays.copyOf(missingSymbols.toArray(), missingSymbols.toArray().length, String[].class);
    }

    private String[] getAllSymbolsFromTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        HashSet<String> symbolsFromTrans = new HashSet<>();

        for (Transaction t : transactions) {
            symbolsFromTrans.add(t.getSymbol());
        }

        return Arrays.copyOf(symbolsFromTrans.toArray(), symbolsFromTrans.toArray().length, String[].class);
    }

    private void updateFromTransactions(boolean onlyMissingSymbols) throws InterruptedException, ParseException {

        long firstReq = 0;
        int reqCount = 0;
        long intervalInSec;

        String[] symbolsToUpdate = (onlyMissingSymbols ? getMissingSymbolsFromTransactions() : getAllSymbolsFromTransactions());

        System.out.println(
                (onlyMissingSymbols ? "Updating missing symbols:" : "Updating all symbols")
        );

        System.out.println(Arrays.toString(symbolsToUpdate));

        TickerFromUrl tfu = new TickerFromUrl("TIME_SERIES_DAILY", StockTrackerApi.API_KEY);

        for(String currentSymbol : symbolsToUpdate){

            if(reqCount == 0) firstReq = System.currentTimeMillis();

            intervalInSec = (System.currentTimeMillis() - firstReq)/1000;
            System.out.println("Current interval from firstReq: " + intervalInSec);

            while(reqCount == this.reqLimitPerMin && intervalInSec < 60) {
                intervalInSec = (System.currentTimeMillis() - firstReq)/1000;
                System.out.println("Reached 5 reqs. Waiting for: " + (60-intervalInSec));
                Thread.sleep(2000);
            }

            if(reqCount == 5){
                reqCount = 0;
                firstReq = System.currentTimeMillis();
            }

            //delete all ticker for that symbol, in case there is more than one
            List<Symbol> tickersToDelete = symbolRepository.deleteBySymbol(currentSymbol);
            if(tickersToDelete != null) {
                for (Symbol t : tickersToDelete) {
                    System.out.println("Deleting ticker " + t.getSymbol());
                }
            }


            //outputsize: full or compact
            Symbol temp = tfu.getTicker(currentSymbol,"compact");




            PriceReader pr = new PriceReader(temp, "Time Series (Daily)");

            System.out.println("Saving Ticker " + temp.getSymbol());
            symbolRepository.save(pr.setClosingPrices());
            Thread.sleep(1000);
            reqCount++;
            }

    }


    @Override
    public void run() {
        System.out.println("Starting TickerUpdater thread");
        try {
            updateFromTransactions(this.onlyMissingSymbols);
        } catch (InterruptedException | ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Ending TickerUpdater thread");
    }
}
