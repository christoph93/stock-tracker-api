package com.stocktrackerapi.config;



import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import javax.annotation.PostConstruct;

@Configuration
public class MongoConfiguration extends AbstractMongoConfiguration {

    /*
    spring.data.mongodb.uri=mongodb+srv://admin:admin@christoph-fqybv.mongodb.net/sample_mflix?retryWrites=true&w=majority
    spring.data.mongodb.repositories.enabled=true
    spring.data.mongodb.host=127.0.0.1
    spring.data.mongodb.port=27017

     */

    @Value("${spring.data.mongodb.database:test}")
    private String database;


    @Value("${spring.data.mongodb.uri:mongodb+srv://admin:admin@christoph-fqybv.mongodb.net/test?retryWrites=true&w=majority}")
    private MongoClientURI uri;


     /*

    @Value("${spring.data.mongodb.host:localhost}:${spring.data.mongodb.port:27017}")
    private String host;

      */




    @Autowired
    private MappingMongoConverter mongoConverter;

    // Converts . into a mongo friendly char
    @PostConstruct
    public void setUpMongoEscapeCharacterConversion() {
        mongoConverter.setMapKeyDotReplacement("_");
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(uri);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }
}
