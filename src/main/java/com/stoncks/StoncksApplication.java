package com.stoncks;


import com.stoncks.document.Portfolio;
import com.stoncks.document.Ticker;
import com.stoncks.document.Transaction;

import com.stoncks.io.PortfolioManager;
import com.stoncks.io.PriceReader;
import com.stoncks.repository.PortfolioRepository;
import com.stoncks.repository.TickerRepository;
import com.stoncks.repository.TransactionRepository;
import com.stoncks.io.ExcelReader;
import com.stoncks.service.TickerUpdater;
import jdk.vm.ci.meta.Local;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.util.BsonUtils;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class StoncksApplication implements CommandLineRunner {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TickerRepository tickerRepository;

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

        //saveToMongo(readExcel("/home/mx/IdeaProjects/stoncks/transactions.xls"));
        /*Thread t1 = new Thread(new TickerUpdater(transactionRepository, tickerRepository, 5, false));
        t1.start();*/

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date today =  Date.from(Instant.now());
        Date todayNoTime = formatter.parse(formatter.format(today));

        for(Ticker t : tickerRepository.findAll()){
            LinkedHashMap<String, Double> closingPricesStringDouble = (LinkedHashMap<String, Double>) t.getClosingPrices();

            //convert to <LocalDate, Double>
            LinkedHashMap<Date, Double> closingPricesLocalDate = new LinkedHashMap<>();
            for(String s : closingPricesStringDouble.keySet()){
                closingPricesLocalDate.put(formatter.parse(s), closingPricesStringDouble.get(s));
            }

            //get last price
            ArrayList<Date> datesList = new ArrayList<>(closingPricesLocalDate.keySet());
            Collections.sort(datesList);
            System.out.println("Latest date for: " + t.getSymbol() + " " + datesList.get(datesList.size()-1));
            System.out.println("Latest price: " + closingPricesLocalDate.get(datesList.get(datesList.size()-1)));




            /* print ordered */
            /*
            closingPricesLocalDate.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Date, Double>comparingByKey())
                    .forEach( System.out::println);
                    */

        }






       /* PriceReader priceReader;

        for(Ticker ticker : tickerRepository.findAll()){
            priceReader = new PriceReader(ticker, "Time Series (Daily)");
            System.out.println(ticker.getSymbol() + "\n" + priceReader.getPricesAsMap());
        }*/

/*
        portfolioRepository.deleteAll();

        //create portfolio
        portfolioRepository.save(new Portfolio("MyPortfolio", new String[]{"LEVE3.SAO", "VALE3.SAO"}, "MyOwner"));

        //Update portfolio
        PortfolioManager portfolioManager = new PortfolioManager(portfolioRepository);

        //add a symbol
        System.out.println(Arrays.toString(portfolioManager.addSymbol("MyPortfolio", "MyOwner", "AEFI11.SAO")));
        //remove a symbol
        System.out.println(Arrays.toString(portfolioManager.removeSymbol("MyPortfolio", "MyOwner", "LEVE3.SAO")));
        //remove all symbols
        System.out.println(portfolioManager.removeAllSymbols("MyPortfolio", "MyOwner"));
        //add symbols as list
        System.out.println(Arrays.toString(portfolioManager.addSymbols("MyPortfolio", "MyOwner",Arrays.asList("POMO3.SAO", "USIM3.SAO"))));
        System.out.println(Arrays.toString(portfolioManager.addSymbols("MyPortfolio", "MyOwner",Arrays.asList("POMO3.SAO", "USIM3.SAO"))));
        System.out.println(Arrays.toString(portfolioManager.addSymbols("MyPortfolio", "MyOwner",Arrays.asList("ITSA4.SAO", "TIET11.SAO"))));
        //update name
        System.out.println(portfolioManager.updateName("MyPortfolio", "MyOwner", "MyNewPortfolio"));
        //update owner
        System.out.println(portfolioManager.updateName("MyNewPortfolio", "MyOwner", "MyNewOwner"));
*/





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