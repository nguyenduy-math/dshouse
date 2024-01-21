package com.bigdata.apprunner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.bson.Document;

@RestController
@RequestMapping(value="/test-apis", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
    // @Value("${aws.sqs.queueName}")
    // private String queueName;
    // @Autowired
    // private QueueMessagingTemplate queueMessagingTemplate;

    @GetMapping("/talk/{text}")
    public ResponseEntity<Document> test(
            @PathVariable String text
    ) {
        var doc = new Document();
        doc.put("message","Hello World," + text);

        return new ResponseEntity<Document>(doc, HttpStatus.OK);
    }

    @PostMapping("/aws/send/{text}")
    public ResponseEntity<Document> sendMsg(
            @PathVariable String text
    ) {
        var doc = new Document();
        doc.put("message","Hello World," + text);

        // queueMessagingTemplate.convertAndSend(queueName, doc.get("message"));

        return new ResponseEntity<Document>(doc, HttpStatus.OK);
    }
}
