package com.bigdata.newmission.controller;

import com.bigdata.newmission.domain.FileDTO;
import com.bigdata.newmission.redis.model.RedisCompany;
import com.bigdata.newmission.redis.service.RedisCompanyService;
import com.bigdata.newmission.service.AzureBlobService;
import com.bigdata.newmission.service.MongoDataService;
import com.bigdata.newmission.service.VaultService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.messaging.MessageHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/azure")
public class AzureBlobController {
    // private final HttpServletRequest httpServletRequest;

    @Autowired
    private AzureBlobService azureBlobService;

    @Autowired
    private VaultService vaultService;

    @GetMapping(value = "/getBlob/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity getAzureBlob(@PathVariable String id) {
        FileDTO fileDTO = azureBlobService.getBlob(id);
        if (fileDTO == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        InputStreamResource isR = new InputStreamResource(fileDTO.getInputStream());
        HttpHeaders headersResponse = new HttpHeaders();
        headersResponse.add("content-type", fileDTO.getContentType());

        return new ResponseEntity(isR, headersResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/uploadBlob", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> putAzureBlob(@RequestHeader Map<String, String> headers,
            @RequestPart(required = false) MultipartFile file) throws Exception {
        String blobName = headers.get(Constant.AzureBlobHeaders.HEADER_AZURE_BLOB_NAME);
        if (file != null && !file.isEmpty()) {
            Map<String, String> meta = new HashMap<>();
            meta.put("blobName", blobName);
            var azureBlob = azureBlobService.uploadBlob(meta, file.getInputStream());

            var token = vaultService.token();

            if (azureBlob != null) {
                return new ResponseEntity<>(azureBlob, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
