package com.stoncks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.stoncks.document.TransactionDocument;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends MongoRepository<TransactionDocument, String>{


    Optional<List<TransactionDocument>> findBySymbol(String symbol);


    }
