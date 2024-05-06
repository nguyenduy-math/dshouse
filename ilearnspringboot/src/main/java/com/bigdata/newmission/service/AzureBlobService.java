package com.bigdata.newmission.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.bigdata.newmission.domain.FileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
//@Slf4j
public class AzureBlobService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AzureBlobService.class);
    @Autowired
    private BlobContainerClient blobContainerClient;

    public FileDTO getBlob(String blobName) {
        FileDTO fileDTO = null;
        try{
            BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
            if (blobClient.exists()){
                fileDTO = new FileDTO();
                InputStream inputStream = blobClient.openInputStream();
                String contentType = blobClient.getProperties().getContentType();
                fileDTO.setInputStream(inputStream);
                fileDTO.setContentType(contentType);
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }

        return fileDTO;
    }

    public boolean uploadBlob(Map<String, String> metadata, InputStreamResource is, String blobName){
        boolean isOk = false;
        try{
            BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
            byte[] data = getBytesFromInputStream(is.getInputStream());

            InputStream inputStream = new ByteArrayInputStream(data);
            blobClient.upload(inputStream, data.length);
            blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType("application/octet-stream"));

            Map<String, String> tags = new HashMap<>();
            tags.put("ttl","oneDay");

            blobClient.setTags(tags);

            isOk = true;
        }catch( Exception ex){
            log.error(ex.getMessage());
        }
        return isOk;
    }

    private byte[] getBytesFromInputStream(InputStream is) throws Exception{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        for (int len = is.read(buffer);len != -1; len = is.read(buffer)){
            os.write(buffer,0,len);
        }

        return os.toByteArray();
    }

}
