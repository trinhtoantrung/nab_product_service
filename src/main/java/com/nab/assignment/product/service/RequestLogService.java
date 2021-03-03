package com.nab.assignment.product.service;

import javax.servlet.http.HttpServletRequest;

public interface RequestLogService {
    void logRequest(HttpServletRequest httpServletRequest, Object body);
}
