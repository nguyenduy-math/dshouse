package com.bigdata.apprunner.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.bson.Document;

@RestController
@RequestMapping(value="/test-apis", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
    @GetMapping("/test/{text}")
    public ResponseEntity<Document> test(
            @PathVariable String text
    ) {
        var doc = new Document();
        doc.put("message","Hello World," + text);

        return new ResponseEntity<Document>(doc, HttpStatus.OK);
    }
}
