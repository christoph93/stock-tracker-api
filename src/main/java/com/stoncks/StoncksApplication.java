package com.stoncks;


import com.stoncks.document.Portfolio;
import com.stoncks.document.Transaction;

import com.stoncks.entity.Position;
import com.stoncks.manager.PortfolioManager;
import com.stoncks.repository.PortfolioRepository;
import com.stoncks.repository.SymbolRepository;
import com.stoncks.repository.TransactionRepository;
import com.stoncks.io.ExcelReader;
import com.stoncks.service.TickerUpdater;
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

    public static void main(String[] args) {
        SpringApplication.run(StoncksApplication.class, args);
    }

    public static String API_KEY =  "N6UZN5PBXVO599CV";

    @Override
    @SuppressWarnings("unchecked")
    public void run(String[] args) throws Exception {

        //Upload transactions to mongodb

        //saveToMongo(readExcel("./transactions.xls"));
        Thread t1 = new Thread(new TickerUpdater(transactionRepository, symbolRepository, 5, true));
        t1.start();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date today =  Date.from(Instant.now());
        Date todayNoTime = formatter.parse(formatter.format(today));

       /* PriceReader priceReader;

        for(Ticker ticker : tickerRepository.findAll()){
            priceReader = new PriceReader(ticker, "Time Series (Daily)");
            System.out.println(ticker.getSymbol() + "\n" + priceReader.getPricesAsMap());
        }*/


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



        String name = "Portfolio 1";
        String owner = "Owner 1";

        PortfolioManager portfolioManager = new PortfolioManager(portfolioRepository,symbolRepository,transactionRepository);

        Portfolio testPortfolio = portfolioManager.createPortfolio(owner, name);

        testPortfolio.addSymbols(Arrays.asList(symbols));
        portfolioManager.generatePositions(testPortfolio);

        for(Position position : testPortfolio.getPositions()){
                System.out.println(position);
            }



    }




    public ArrayList<Transaction> readExcel(String path) throws ParseException {
        ArrayList<String[]> table;
        String line = "";
        ArrayList<Transaction> transactions = new ArrayList<>();

        ExcelReader er = new ExcelReader();

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


    public void saveToMongo(ArrayList<Transaction> transactions){


        transactionRepository.deleteAll();
        transactionRepository.saveAll(transactions);

        System.out.println("Saved the following transactions");

        for (Transaction t : transactionRepository.findAll()) {
            System.out.println(t);
        }
    }


}