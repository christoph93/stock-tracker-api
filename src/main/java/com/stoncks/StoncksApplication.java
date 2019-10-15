package com.stoncks;

import com.fasterxml.jackson.databind.JsonNode;
import com.stoncks.document.Ticker;
import com.stoncks.document.Transaction;
import com.stoncks.io.TickerFromUrl;
import com.stoncks.repository.TickerRepository;
import com.stoncks.repository.TransactionRepository;
import com.stoncks.io.ExcelReader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class StoncksApplication implements CommandLineRunner {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TickerRepository tickerRepository;

    public static void main(String[] args) {
        SpringApplication.run(StoncksApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {

        /*
        saveToMongo(
        readExcel("/home/mx/IdeaProjects/stoncks/transactions.xls")
        );
         */


        TickerFromUrl ticker = new TickerFromUrl();
        JsonNode tickerAsJson = ticker.tickerDaily("PETR4.SAO");
        Ticker t = new Ticker(tickerAsJson);

        tickerRepository.deleteAll();
        tickerRepository.save(t);

        System.out.println(tickerAsJson.toPrettyString());
    }




    public ArrayList<Transaction> readExcel(String path){
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


        for(int i = 1; i < table.size(); i++){
            transactions.add(new Transaction(
                    table.get(i)[0].trim(),
                    table.get(i)[2].trim(),
                    table.get(i)[5].trim(),
                    table.get(i)[6].trim(),
                    Double.parseDouble(table.get(i)[7]),
                    Double.parseDouble(table.get(i)[8]),
                    Double.parseDouble(table.get(i)[9]),
                    System.currentTimeMillis()
            ));
        }

    return transactions;

    }


    public void saveToMongo(ArrayList<Transaction> transactions){


        transactionRepository.deleteAll();
        transactionRepository.saveAll(transactions);


        System.out.println("Transactions found with findAll():");
        System.out.println("-------------------------------");
        for (Transaction t : transactionRepository.findAll()) {
            System.out.println(t);
        }

/*        repository.deleteAll();

        // save a couple of customers
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }*/

            /*
        System.out.println("Comments found with findAll():");
        System.out.println("-------------------------------");
        for (Comments c : repository.findAll()) {
            System.out.println(c);
        } */
    }


}