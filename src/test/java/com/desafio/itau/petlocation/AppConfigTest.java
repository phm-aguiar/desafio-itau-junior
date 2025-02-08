package com.desafio.itau.petlocation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class AppConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testRestTemplateBeanCreated() {
        RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
        assertNotNull(restTemplate, "RestTemplate bean should be created and not null");
    }
}