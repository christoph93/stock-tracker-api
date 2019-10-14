package com.stoncks;

import com.stoncks.document.Comments;
import com.stoncks.document.Customer;
import com.stoncks.document.Transaction;
import com.stoncks.repository.CommentsRepository;
import com.stoncks.repository.CustomerRepository;
import com.stoncks.repository.TransactionRepository;
import jdk.vm.ci.meta.Local;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.crypto.dsig.TransformService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class StoncksApplication implements CommandLineRunner {

    @Autowired
    private TransactionRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(StoncksApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {

        ArrayList<String[]> table;
        String line = "";
        ArrayList<Transaction> transactions = new ArrayList<>();

        ExcelReader er = new ExcelReader();

        System.out.println("Calling readFile");

        table = er.findTableInFile("/home/mx/IdeaProjects/stoncks/transactions.xls");

        for(String[] row : table){
            line = "";
            for(String s : row){
                line += s + " ";
            }
            System.out.println(line);
        }

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");


        for(int i = 1; i < table.size(); i++){
            transactions.add(new Transaction(
                    table.get(i)[0].trim(),
                    table.get(i)[2].trim(),
                    table.get(i)[5].trim(),
                    table.get(i)[6].trim(),
                    Double.parseDouble(table.get(i)[7]),
                    Double.parseDouble(table.get(i)[8]),
                    Double.parseDouble(table.get(i)[9])
            ));
        }

        repository.deleteAll();
        repository.saveAll(transactions);

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
        }
*/
    }


}