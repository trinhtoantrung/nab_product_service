package com.nab.assignment.product.controller;

import com.nab.assignment.product.dto.AppInfoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AboutControllerTest {

    @InjectMocks
    private AboutController aboutController;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(aboutController, "name", "product-service");
        ReflectionTestUtils.setField(aboutController, "version", "0.0.1-SNAPSHOT");
        ReflectionTestUtils.setField(aboutController, "description", "Product Service");
    }

    @Test
    void getInfo() {
        AppInfoDTO appInfoDTO = aboutController.getInfo();
        assertEquals("product-service", appInfoDTO.getAppName());
        assertEquals("0.0.1-SNAPSHOT", appInfoDTO.getVersion());
        assertEquals("Product Service", appInfoDTO.getDescription());
    }
}