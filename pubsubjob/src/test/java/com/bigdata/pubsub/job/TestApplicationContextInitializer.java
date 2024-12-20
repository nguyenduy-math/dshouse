package com.bigdata.pubsub.job;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class TestApplicationContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'initialize'");
        System.setProperty("AZURE_CLIENT_ID", "0e15ebd5-a17e-4e53-ab42-dac9cd690d19");
        System.setProperty("AZURE_CLIENT_SECRET", "68c8Q~7M2yq.jP-4eQDYX6sAI7LWNsJuBDOWzb_A");
        System.setProperty("AZURE_TENANT_ID", "c596bb4b-3af1-420f-89f1-672f8c6dce60");
    }

}
