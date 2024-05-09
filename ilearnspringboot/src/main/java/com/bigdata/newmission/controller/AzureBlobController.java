package com.bigdata.newmission.controller;

import com.bigdata.newmission.domain.FileDTO;
import com.bigdata.newmission.service.AzureBlobService;
import com.bigdata.newmission.service.MongoDataService;
import com.bigdata.newmission.utility.Constant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/api/azure")
public class AzureBlobController {
//    private final HttpServletRequest httpServletRequest;

    @Autowired
    private MongoDataService mongoDataService;

    @Autowired
    private AzureBlobService azureBlobService;

    @GetMapping(value="/test")
    public ResponseEntity<?> test(){
        return new ResponseEntity<>(mongoDataService.getCount("companies"), HttpStatus.OK);
    }

    @GetMapping(value="/getBlob", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity getAzureBlob(@RequestHeader Map<String, String> headers){
        String blobName = headers.get(Constant.AzureBlobHeaders.HEADER_BLOBNAME);
        FileDTO fileDTO = azureBlobService.getBlob(blobName);
        if (fileDTO == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        InputStreamResource isR = new InputStreamResource(fileDTO.getInputStream());
        HttpHeaders headersResponse = new HttpHeaders();
        headersResponse.add("content-type", fileDTO.getContentType());

        return new ResponseEntity(isR, headersResponse, HttpStatus.OK);
    }

    @PutMapping(value="/uploadBlob")
    public ResponseEntity<?> putAzureBlob(@RequestHeader Map<String, String> headers,
                                       @RequestBody(required = false) InputStreamResource file) {
        String blobName = headers.get(Constant.AzureBlobHeaders.HEADER_BLOBNAME);
        String docId = UUID.randomUUID().toString();
        if (file != null && file.exists()){
            if (azureBlobService.uploadBlob(null, file, docId)){

                mongoDataService.createAzureBlobDoc(docId, "azureblogdocs");

                return new ResponseEntity<>(docId, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
