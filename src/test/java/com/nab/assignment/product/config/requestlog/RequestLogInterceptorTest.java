package com.nab.assignment.product.config.requestlog;

import com.nab.assignment.product.service.RequestLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestLogInterceptorTest {

    @InjectMocks
    private RequestLogInterceptor requestLogInterceptor;

    @Mock
    private RequestLogService requestLogService;

    @BeforeEach
    void setUp() {
        requestLogInterceptor = new RequestLogInterceptor(requestLogService);
    }

    @Test
    void preHandle() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpServletRequest.setDispatcherType(DispatcherType.ERROR);

        // branch 1
        assertEquals(true, requestLogInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, null));

        // branch 2
        mockHttpServletRequest.setDispatcherType(DispatcherType.REQUEST);
        assertEquals(true, requestLogInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, null));
        verify(requestLogService, times(1)).logRequest(mockHttpServletRequest, null);
    }
}