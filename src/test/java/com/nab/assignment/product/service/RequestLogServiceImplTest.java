package com.nab.assignment.product.service;

import com.nab.assignment.product.constant.ApplicationConstants;
import com.nab.assignment.product.dto.request.UpdateProductPriceDTO;
import com.nab.assignment.product.model.RequestLog;
import com.nab.assignment.product.repository.RequestLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestLogServiceImplTest {
    @Mock
    private RequestLogRepository requestLogRepository;

    @Mock
    private HttpServletRequest request;

    private RequestLogService requestLogService;

    @BeforeEach
    void setUp() {
        requestLogService = new RequestLogServiceImpl(requestLogRepository);
    }

    @Test
    void logRequest() {
        UUID id = UUID.randomUUID();
        when(request.getAttribute(ApplicationConstants.REQUEST_ID)).thenReturn(id);
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("http://localhost:8081");
        when(request.getQueryString()).thenReturn("id=" + UUID.randomUUID().toString() + "&quantity=4");

//---content-type : application/json
//---user-agent : PostmanRuntime/7.26.8
//---accept : */*
//---cache-control : no-cache
//---postman-token : 92659a87-7f48-4f8f-87f7-c3e68c04311d
//---host : localhost:8082
//---accept-encoding : gzip, deflate, br
//---connection : keep-alive
//---content-length : 115

        List<String> headerNames = new ArrayList<>();
        headerNames.add("content-type");
        headerNames.add("user-agent");
        headerNames.add("accept");

        // creating object of type Enumeration<String>
        Enumeration<String> headerEnumerations = Collections.enumeration(headerNames);
        when(request.getHeaderNames()).thenReturn(headerEnumerations);
        when(request.getHeader("content-type")).thenReturn("application/json");
        when(request.getHeader("user-agent")).thenReturn("PostmanRuntime/7.26.8");
        when(request.getHeader("accept")).thenReturn("*/*");

        UpdateProductPriceDTO body = new UpdateProductPriceDTO(UUID.randomUUID().toString(), 1000000L);

        requestLogService.logRequest(request, body);

        ArgumentCaptor<RequestLog> argument = ArgumentCaptor.forClass(RequestLog.class);
        verify(requestLogRepository, times(1)).save(argument.capture());
        assertEquals(id, argument.getValue().getId());

        requestLogService.logRequest(null, body);
        requestLogService.logRequest(request, null);
    }

    @Test
    void logRequestBody() {
        UUID id = UUID.randomUUID();
        UpdateProductPriceDTO body = new UpdateProductPriceDTO(UUID.randomUUID().toString(), 1000000L);

        RequestLog requestLog = new RequestLog();
        requestLog.setId(id);

        when(requestLogRepository.findById(id)).thenReturn(Optional.of(requestLog));
        requestLogService.logRequestBody(id, body);

        ArgumentCaptor<RequestLog> argument = ArgumentCaptor.forClass(RequestLog.class);
        verify(requestLogRepository, times(1)).save(argument.capture());
        assertEquals(id, argument.getValue().getId());
        assertTrue(argument.getValue().getRequestBody() != null);

        requestLogService.logRequestBody(id, null);
    }
}