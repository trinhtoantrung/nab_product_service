package com.nab.assignment.product.jpa.specification.product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchCriteriaTest {

    @Test
    void test() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey("searchString");
        searchCriteria.setOperation(":");
        searchCriteria.setValue("Trung");

        assertEquals("searchString", searchCriteria.getKey());
        assertEquals(":", searchCriteria.getOperation());
        assertEquals("Trung", searchCriteria.getValue());
    }
}