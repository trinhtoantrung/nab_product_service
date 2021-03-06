package com.nab.assignment.product.config.requestlog;

import com.nab.assignment.product.constant.ApplicationConstants;
import com.nab.assignment.product.service.RequestLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/*
* Logging requests
* */

@Component
public class RequestLogInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(RequestLogInterceptor.class);

    private final RequestLogService requestLogService;

    public RequestLogInterceptor(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())) {
            String requestId = UUID.randomUUID().toString();
            request.setAttribute(ApplicationConstants.REQUEST_ID, requestId);

            // async request log
            log.trace("Async request log: {}", requestId);
            requestLogService.logRequest(request, null);
        }

        return true;
    }
}
