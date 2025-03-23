package com.bigdata.pubsub.repository;

import com.bigdata.pubsub.model.Companies;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
//https://www.baeldung.com/spring-graphql
@Repository
public interface CompaniesRepository extends MongoRepository<Companies, String> {
}