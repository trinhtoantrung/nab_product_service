package com.nab.assignment.product.config.requestlog;

import com.nab.assignment.product.constant.ApplicationConstants;
import com.nab.assignment.product.service.RequestLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomRequestBodyAdviceAdapterTest {

    @InjectMocks
    private CustomRequestBodyAdviceAdapter customRequestBodyAdviceAdapter;

    @Mock
    private RequestLogService requestLogService;

    @Mock
    private HttpServletRequest httpServletRequest;

    private MethodParameter methodParameter;
    private Type type;
    private Class<? extends HttpMessageConverter<?>> converterType;
    private Object body;
    private HttpInputMessage inputMessage;

    @BeforeEach
    void setUp() {
        customRequestBodyAdviceAdapter = new CustomRequestBodyAdviceAdapter(requestLogService, httpServletRequest);
    }

    @Test
    void supports() {
        assertTrue(customRequestBodyAdviceAdapter.supports(methodParameter, type, converterType));
    }

    @Test
    void afterBodyRead() {
        when(httpServletRequest.getAttribute(ApplicationConstants.REQUEST_ID)).thenReturn(null);
        customRequestBodyAdviceAdapter.afterBodyRead(body, inputMessage, methodParameter, type, converterType);

        String id = UUID.randomUUID().toString();
        when(httpServletRequest.getAttribute(ApplicationConstants.REQUEST_ID)).thenReturn(id);
        customRequestBodyAdviceAdapter.afterBodyRead(body, inputMessage, methodParameter, type, converterType);
        verify(requestLogService, times(1)).logRequestBody(UUID.fromString(id), body);
    }
}