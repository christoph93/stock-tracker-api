package com.stocktrackerapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stocktrackerapi.document.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

	Optional<List<Transaction>> findBySymbol(String symbol);

}
