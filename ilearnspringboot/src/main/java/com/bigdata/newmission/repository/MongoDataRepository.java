package com.bigdata.newmission.repository;

import com.bigdata.newmission.domain.AzureBlob;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoDataRepository {
    private final static Logger logger = LoggerFactory.getLogger(MongoDataRepository.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public Long getCount(String collectionName) {return mongoTemplate.estimatedCount(collectionName);}

    public Boolean createAzureBlobDoc(AzureBlob blob, String collName){
        var doc = mongoTemplate.save(blob, collName);
        if (doc != null)
            return true;
        return false;
    }
}
