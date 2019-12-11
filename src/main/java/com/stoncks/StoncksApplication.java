package com.stoncks;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stoncks.document.*;

import com.stoncks.entity.Position;
import com.stoncks.io.DividendExcelReader;
import com.stoncks.manager.PortfolioManager;
import com.stoncks.repository.*;
import com.stoncks.io.TransactionExcelReader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class StoncksApplication implements CommandLineRunner {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private DividendRepository dividendRepository;

    @Autowired
    private AliasRepository aliasRepository;

    public static void main(String[] args) {
        SpringApplication.run(StoncksApplication.class, args);
    }

    public static String API_KEY =  "N6UZN5PBXVO599CV";

    @Override
    @SuppressWarnings("unchecked")
    public void run(String[] args) throws Exception {

        //Upload transactions to mongodb

        //saveTransactionsToMongo(readTransactionsExcel("./transactions.xls"));
        //saveDividendsToMongo(readDividendsExcel("./dividends.xls"));
        //Thread t1 = new Thread(new TickerUpdater(transactionRepository, symbolRepository, 5, true));
        //t1.start();

        updateAliases();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date today =  Date.from(Instant.now());
        Date todayNoTime = formatter.parse(formatter.format(today));

//        PriceReader priceReader;
//
//        for(Ticker ticker : tickerRepository.findAll()){
//            priceReader = new PriceReader(ticker, "Time Series (Daily)");
//            System.out.println(ticker.getSymbol() + "\n" + priceReader.getPricesAsMap());
//        }


       //Symbol list from transactions
        HashSet<String> uniqueSymbols = new HashSet<>();

        for(Transaction td : transactionRepository.findAll()){
            uniqueSymbols.add(td.getSymbol()+".SAO");
        }

        System.out.println("unique symbols from transactions");
        System.out.println(uniqueSymbols);

        portfolioRepository.deleteAll();

        String[] symbols = new String[uniqueSymbols.size()];
        uniqueSymbols.toArray(symbols);

        System.out.println("Symbols array");
        for(String s : symbols){
            System.out.println(s);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String name = "Portfolio 1";
        String owner = "Owner 1";

        PortfolioManager portfolioManager = new PortfolioManager(portfolioRepository,symbolRepository,transactionRepository, dividendRepository);

        Portfolio testPortfolio = portfolioManager.createPortfolio(owner, name);

        testPortfolio.addSymbols(Arrays.asList(symbols));
        portfolioManager.generatePositions(testPortfolio);
        portfolioManager.calculateTotalPortfolioProfit(testPortfolio);

        for(Position position : testPortfolio.getPositions()){
                System.out.println(gson.toJson(position));
            }

        System.out.println(gson.toJson(testPortfolio));
    }

    public ArrayList<Transaction> readTransactionsExcel(String path) throws ParseException {
        ArrayList<String[]> table;
        String line = "";
        ArrayList<Transaction> transactions = new ArrayList<>();

        TransactionExcelReader er = new TransactionExcelReader();

        System.out.println("Calling readFile");

        table = er.findTableInFile(path);
/*
        for(String[] row : table){
            line = "";
            for(String s : row){
                line += s + " ";
            }
            System.out.println(line);
        }*/

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);

        //table.get(i)[0].trim()

        for(int i = 1; i < table.size(); i++){
            transactions.add(new Transaction(
                    formatter.parse(table.get(i)[0].trim()),
                    table.get(i)[2].trim(),
                    table.get(i)[5].trim(),
                    table.get(i)[6].trim(),
                    Double.parseDouble(table.get(i)[7]),
                    Double.parseDouble(table.get(i)[8]),
                    Double.parseDouble(table.get(i)[9]),
                    Date.from(Instant.now())
            ));
        }

    return transactions;

    }


    public ArrayList<Dividend> readDividendsExcel(String path) throws ParseException {
        ArrayList<String[]> table;
        String line = "";
        ArrayList<Dividend> dividends = new ArrayList<>();

        DividendExcelReader der = new DividendExcelReader();

        table = der.getTableAsArray(path);

        System.out.println("Calling readFile");

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);

        for(int i = 1; i < table.size(); i++){
            dividends.add(new Dividend(formatter.parse(table.get(i)[0].trim()),
                    table.get(i)[1].trim(),
                    table.get(i)[2].trim(),
                    Double.parseDouble(table.get(i)[3].replace("R$", "").replace(",", ".").trim()),
                    Double.parseDouble(table.get(i)[4].replace("R$", "").replace(",", ".").trim()),
                    Double.parseDouble(table.get(i)[5].replace("R$", "").replace(",", ".").trim())));
        }

        return dividends;

    }


    private void saveTransactionsToMongo(ArrayList<Transaction> transactions){


        transactionRepository.deleteAll();
        transactionRepository.saveAll(transactions);

        System.out.println("Saved the following transactions");

        for (Transaction t : transactionRepository.findAll()) {
            System.out.println(t);
        }
    }

    public void saveDividendsToMongo(ArrayList<Dividend> dividends){

        dividendRepository.deleteAll();
        dividendRepository.saveAll(dividends);

        System.out.println("Saved the following dividends");

        for (Dividend d : dividendRepository.findAll()) {
            System.out.println(d);
        }
    }



    public void updateAliases(){
        List<Alias> aliases = aliasRepository.findAll();
        Optional<Symbol> optSymbol;
        Symbol symbol;

        for(Alias alias : aliases){

            //update symbols
            optSymbol = symbolRepository.findBySymbol(alias.getSymbol());

            if(optSymbol.isPresent()){
                symbol = optSymbol.get();
                symbol.setAlias(alias.getAlias());
            } else {
                System.out.println("Symbol not found for alias " + alias.getAlias());
            }


            //update transactions
            Optional<List<Transaction>> optionalTransactions = transactionRepository.findBySymbol(alias.getSymbol());
            List<Transaction> transactions;
            if(optionalTransactions.isPresent()){
                transactions = optionalTransactions.get();
                for(Transaction transaction : transactions){
                    transaction.setAlias(alias.getAlias());
                }
                transactionRepository.saveAll(transactions);
            }


            //update dividends
            Optional<List<Dividend>> optionalDividends = dividendRepository.findBySymbol(alias.getSymbol());
            List<Dividend> dividends;
            if(optionalDividends.isPresent()){
                dividends = optionalDividends.get();
                for(Dividend dividend : dividends){
                    dividend.setAlias(alias.getAlias());
                }
                dividendRepository.saveAll(dividends);
            }

        }
    }


}