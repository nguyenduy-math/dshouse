package com.bigdata.newmission.config;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//https://learn.microsoft.com/en-us/java/api/overview/azure/storage?view=azure-java-stable
@Configuration
public class AzureBlobStorageConfig {

    @Value("${azure-storage.connectionString}")
    private String azureStorageConnectionString;

    @Value("${azure-storage.container}")
    private String azureStorageContainer;

    @Bean
    public BlobServiceClient blobServiceClient(){
        return new BlobServiceClientBuilder()
                .connectionString(azureStorageConnectionString)
                .buildClient();
    }

    @Bean
    public BlobContainerClient blobContainerClient(){
        return blobServiceClient().getBlobContainerClient(azureStorageContainer);
    }
}
