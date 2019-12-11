package com.stoncks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.stoncks.document.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends MongoRepository<Transaction, String>{


    Optional<List<Transaction>> findByAlias(String alias);
    Optional<List<Transaction>> findBySymbol(String symbol);


    }
