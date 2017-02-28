package com.bigbazaar.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@EnableMongoRepositories(basePackages="com.bigbazaar.backend.repos")
@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration{

	@Override
	protected String getDatabaseName() {
		return "test";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient("127.0.0.1", 27017);
	}
	
	public @Bean MongoTemplate mongoTemplate() throws Exception {
	      return new MongoTemplate(mongo(), getDatabaseName());
	  }
	
	@Override
    protected String getMappingBasePackage() {
        return "com.bigbazaar.backend";
    }

}
