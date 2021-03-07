package com.nab.assignment.product.jpa.specification.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductSpecificationsBuilderTest {

    @InjectMocks
    private ProductSpecificationsBuilder productSpecificationsBuilder;

    private List<SearchCriteria> params;

    @BeforeEach
    void setUp() {
        params = new ArrayList<>();
        ReflectionTestUtils.setField(productSpecificationsBuilder, "params", params);
    }

    @Test
    void with() {
        String key = "searchString";
        String operation = ":";
        String object = "Trung";
        productSpecificationsBuilder.with(key, operation, object);
        assertEquals(1, params.size());
        assertEquals("searchString", params.get(0).getKey());
        assertEquals(":", params.get(0).getOperation());
        assertEquals("Trung", params.get(0).getValue());
    }

    @Test
    void build() {
        assertEquals(null, productSpecificationsBuilder.build());

        productSpecificationsBuilder.with("searchString", ":", "Trung");

        productSpecificationsBuilder.build();

        productSpecificationsBuilder.with("minPrice", ">", 10000L);

        productSpecificationsBuilder.build();
    }
}