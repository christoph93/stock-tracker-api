package com.stoncks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.stoncks.document.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String>{


    public interface CustomerRepository extends MongoRepository<Transaction, String> {

        public Transaction findByTransactionID(String transID);
        public List<Transaction> findByCode(String code);
        public List<Transaction> findByDate(Date date);

    }


}
