package com.bigdata.pubsub.job;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
// @ContextConfiguration(initializers = TestApplicationContextInitializer.class)
// @RunWith(SpringJUnit4ClassRunner.class)
class PubsubApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test1() {
        System.out.println("Hello World");
    }
}
