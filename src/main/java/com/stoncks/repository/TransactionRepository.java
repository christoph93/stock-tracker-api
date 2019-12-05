package com.stoncks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.stoncks.document.TransactionDocument;

public interface TransactionRepository extends MongoRepository<TransactionDocument, String>{



    }
