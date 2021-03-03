package com.nab.assignment.product.config.requestlog;

import com.nab.assignment.product.constant.ApplicationConstants;
import com.nab.assignment.product.service.RequestLogService;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/*
* Logging requests (GET methods)
* */

@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    private final RequestLogService requestLogService;

    public RequestLogInterceptor(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
                && request.getMethod().equals(HttpMethod.GET.name())) {
            String requestId = UUID.randomUUID().toString();
            request.setAttribute(ApplicationConstants.REQUEST_ID, requestId);
            requestLogService.logRequest(request, null);
        }

        return true;
    }
}
