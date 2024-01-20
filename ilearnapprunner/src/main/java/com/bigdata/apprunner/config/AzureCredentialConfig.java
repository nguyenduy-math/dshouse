package com.bigdata.apprunner.config;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@Configuration
public class AzureCredentialConfig {
    /*@Bean
    public TokenCredential getTokenCredential(){
        return new DefaultAzureCredentialBuilder().build();
    }*/

    @Bean
    @Qualifier("vaultcred")
    public TokenCredential getTokenCredential() throws  Exception{
        String clientId = System.getenv("AZURE_CLIENT_ID");
        String clientSecret = getClientSecret();
        String tenantId = System.getenv("AZURE_TENANT_ID");
        return new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();
    }

    private String getClientSecret() throws  Exception{
        String clientSecretFilepath = System.getenv("AZURE_CLIENT_SECRET_FILE_PATH");
        if (clientSecretFilepath != null)
            return getClientSecretFromFile(clientSecretFilepath);

        String clientSecret = System.getenv("AZURE_CLIENT_SECRET");
        if (clientSecret == null)
            throw new Exception("AZURE_CLIENT_SECRET or AZURE_CLIENT_SECRET_FILE_PATH sould be available in env variables");

        return clientSecret;
    }

    private String getClientSecretFromFile(String filepath) throws IOException{
        File file = new File(filepath);
        Path path = Paths.get(file.getPath());
        return Files.readString(path);
    }
}
