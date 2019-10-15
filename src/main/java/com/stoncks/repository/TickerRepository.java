package com.stoncks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stoncks.document.Ticker;
import java.util.List;

public interface TickerRepository extends MongoRepository<Ticker, String>{


    public interface CustomerRepository extends MongoRepository<Ticker, String> {

        public Ticker findByTransactionID(String transID);
        public List<Ticker> findByCode(String code);
        public List<Ticker> findByDate(String date);

    }


}
