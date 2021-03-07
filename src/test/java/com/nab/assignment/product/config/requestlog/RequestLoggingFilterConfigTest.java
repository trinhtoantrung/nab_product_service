package com.nab.assignment.product.config.requestlog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RequestLoggingFilterConfigTest {

    @InjectMocks
    private RequestLoggingFilterConfig requestLoggingFilterConfig;

    @BeforeEach
    void setUp() {
    }

    @Test
    void logFilter() {
        CommonsRequestLoggingFilter filter = requestLoggingFilterConfig.logFilter();
        assertTrue(CommonsRequestLoggingFilter.class.isInstance(filter));
    }
}