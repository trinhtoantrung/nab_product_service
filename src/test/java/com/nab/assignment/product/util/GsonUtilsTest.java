package com.nab.assignment.product.util;

import com.google.gson.GsonBuilder;
import com.nab.assignment.product.dto.BrandDTO;
import com.nab.assignment.product.dto.ColorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GsonUtilsTest {

    private ColorDTO colorDTO;

    @BeforeEach
    void setUp() {
        colorDTO = new ColorDTO();
        colorDTO.setCode("red");
        colorDTO.setName("Red");
    }

    @Test
    void toJsonPretty() {
        GsonUtils.toJsonPretty(colorDTO);
    }

    @Test
    void toJson() {
        String result = GsonUtils.toJson(colorDTO);
        System.out.println(result);
    }

    @Test
    void fromJson() {
        String jsonString = "{\"code\":\"red\",\"name\":\"Red\"}";
        ColorDTO colorDTO = GsonUtils.fromJson(jsonString, ColorDTO.class);
        System.out.println(colorDTO);
    }
}