package com.nab.assignment.product.service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface RequestLogService {
    void logRequest(HttpServletRequest httpServletRequest, Object body);
    void logRequestBody(UUID id, Object body);
}
