package com.bigdata.newmission.service;

import com.bigdata.newmission.domain.AzureBlob;
import com.bigdata.newmission.repository.MongoDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoDataService {

    @Autowired
    private MongoDataRepository mongoDataRepository;

    public Long getCount(String collName) {return mongoDataRepository.getCount(collName);}

    public Boolean createAzureBlobDoc(String id, String collName){
        var doc = new AzureBlob();
        doc.setDocId(id);
        return mongoDataRepository.createAzureBlobDoc(doc, collName);
    }
}
