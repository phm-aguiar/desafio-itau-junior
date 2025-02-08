package com.desafio.itau.petlocation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.desafio.itau.petlocation.application.service.PetLocationServiceImpl;
import com.desafio.itau.petlocation.infrastructure.adapter.out.PositionStackAdapter;

@SpringBootTest
class PetlocationApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
    }

    @Test
    void testPetLocationServiceImplBeanLoaded() {
        assertNotNull(applicationContext.getBean(PetLocationServiceImpl.class), "PetLocationServiceImpl bean should be loaded");
    }

    @Test
    void testGeolocationServiceBeanLoaded() {
        assertNotNull(applicationContext.getBean(PositionStackAdapter.class), "GeolocationService bean should be loaded");
    }

    @Test
    void testPositionStackAdapterBeanLoaded() {
        assertNotNull(applicationContext.getBean(PositionStackAdapter.class), "PositionStackAdapter bean should be loaded");
    }

    @Test
    void testMain() {
        PetlocationApplication.main(new String[] {});
        assertNotNull(applicationContext, "Application context should be loaded");
    }
}