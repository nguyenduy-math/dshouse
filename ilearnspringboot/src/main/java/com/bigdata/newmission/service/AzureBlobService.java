package com.bigdata.newmission.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.bigdata.newmission.domain.AzureBlob;
import com.bigdata.newmission.domain.FileDTO;
import com.bigdata.newmission.redis.model.RedisCompany;
import com.bigdata.newmission.redis.service.RedisCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
//@Slf4j
public class AzureBlobService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AzureBlobService.class);
    @Autowired
    private BlobContainerClient blobContainerClient;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisCompanyService redisCompanyService;

    @Autowired
    private MessageHandler messageSender;

    public FileDTO getBlob(String blobid) {
        FileDTO fileDTO = null;
        try{
            var doc = mongoTemplate.findById(blobid, AzureBlob.class, "azureblobdocs");
            if (doc != null) {
                BlobClient blobClient = blobContainerClient.getBlobClient(doc.getBlobPath());
                if (blobClient.exists()) {
                    fileDTO = new FileDTO();
                    InputStream inputStream = blobClient.openInputStream();
                    String contentType = blobClient.getProperties().getContentType();
                    fileDTO.setInputStream(inputStream);
                    fileDTO.setContentType(contentType);
                }
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }

        return fileDTO;
    }

    public AzureBlob uploadBlob(Map<String, String> metadata, InputStream is){
        AzureBlob azBlob = new AzureBlob();
        try{
            String uuid = UUID.randomUUID().toString();

            String blobPath = "vr" + "/" + uuid;

            azBlob.setDocId(uuid);
            azBlob.setBlobPath(blobPath);

            BlobClient blobClient = blobContainerClient.getBlobClient(blobPath);
            byte[] data = getBytesFromInputStream(is);

            InputStream inputStream = new ByteArrayInputStream(data);
            blobClient.upload(inputStream, data.length);
            blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType("application/octet-stream"));

            if (metadata != null)
                blobClient.setMetadata(metadata);

            Map<String, String> tags = new HashMap<>();
            tags.put("ttl","oneDay");

            blobClient.setTags(tags);

            //1
            mongoTemplate.save(azBlob, "azureblobdocs");

            //2
            var redisComp = new RedisCompany();
            redisComp.set_id("1");
            redisComp.setName(uuid);
            redisCompanyService.save(redisComp);
        }catch( Exception ex){
            log.error(ex.getMessage());
        }
        return azBlob;
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
