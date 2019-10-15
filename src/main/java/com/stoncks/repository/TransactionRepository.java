package com.stoncks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.stoncks.document.Transaction;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String>{



    }
