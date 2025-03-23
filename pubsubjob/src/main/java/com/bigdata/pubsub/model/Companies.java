package com.bigdata.pubsub.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companies")
public class Companies {
    @Id
    private String id;

    private String name;
}
