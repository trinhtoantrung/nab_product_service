package com.nab.assignment.product.config.requestlog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@ExtendWith(MockitoExtension.class)
class RequestLogConfigTest {

    @InjectMocks
    private RequestLogConfig requestLogConfig;

    @Mock
    private RequestLogInterceptor requestLogInterceptor;

    @Mock
    private InterceptorRegistry registry;

    @BeforeEach
    void setUp() {
        requestLogConfig = new RequestLogConfig(requestLogInterceptor);
    }

    @Test
    void addInterceptors() {
        requestLogConfig.addInterceptors(registry);
    }
}