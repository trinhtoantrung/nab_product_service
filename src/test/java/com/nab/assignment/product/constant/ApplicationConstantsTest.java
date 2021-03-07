package com.nab.assignment.product.constant;

import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

class ApplicationConstantsTest {

    @Test
    void test() {
        ApplicationConstants applicationConstants = new ApplicationConstants();
        assertNotNull(applicationConstants);
    }
}