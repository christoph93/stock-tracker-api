package com.stoncks.repository;

import com.stoncks.document.PortfolioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PortfolioRepository extends MongoRepository<PortfolioDocument, String> {


    List<PortfolioDocument> findByOwner(String owner);

    @Query("{ 'name' : ?0 , 'owner' : ?1}")
    PortfolioDocument findByNameAndOwner(String name, String owner);

}
