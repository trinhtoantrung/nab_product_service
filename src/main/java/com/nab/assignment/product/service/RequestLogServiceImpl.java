package com.nab.assignment.product.service;

import com.nab.assignment.product.constant.ApplicationConstants;
import com.nab.assignment.product.model.RequestLog;
import com.nab.assignment.product.repository.RequestLogRepository;
import com.nab.assignment.product.util.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.UUID;

@Service
public class RequestLogServiceImpl implements RequestLogService {
    private static final Logger log = LoggerFactory.getLogger(RequestLogServiceImpl.class);

    private final RequestLogRepository requestLogRepository;

    public RequestLogServiceImpl(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    @Async
    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        if (httpServletRequest != null) {
            StringBuilder data = new StringBuilder();

            String requestId = httpServletRequest.getAttribute(ApplicationConstants.REQUEST_ID).toString();

            data.append("\nLOGGING REQUEST-----------------------------------\n")
                    .append("[REQUEST-ID]: ").append(requestId).append("\n")
                    .append("[METHOD]: ").append(httpServletRequest.getMethod()).append("\n")
                    .append("[PATH]: ").append(httpServletRequest.getRequestURI()).append("\n")
                    .append("[QUERIES]: ").append(httpServletRequest.getQueryString()).append("\n")
                    .append("[HEADERS]: ").append("\n");

            StringBuilder headers = new StringBuilder();
            Enumeration headerNames = httpServletRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = httpServletRequest.getHeader(key);
                data.append("---").append(key).append(" : ").append(value).append("\n");
                headers.append("---").append(key).append(" : ").append(value).append("\n");
            }

            if (body != null) {
                data.append("[BODY REQUEST]: ").append("\n").append(GsonUtils.toJsonPretty(body)).append("\n");
            }

            data.append("LOGGING REQUEST-----------------------------------\n");

            log.info(data.toString());

            RequestLog requestLog = new RequestLog();
            requestLog.setId(UUID.fromString(requestId));
            requestLog.setPath(httpServletRequest.getRequestURI());
            requestLog.setQueries(httpServletRequest.getQueryString());
            requestLog.setRequestHeaders(headers.toString());
            requestLog.setMethod(httpServletRequest.getMethod());

            if (body != null) {
                requestLog.setRequestBody(GsonUtils.toJson(body));
            }

            requestLogRepository.save(requestLog);
        }
    }

    @Async
    @Override
    public void logRequestBody(UUID id, Object body) {
        if (body != null) {
            log.info("[BODY REQUEST]: \n {}", GsonUtils.toJsonPretty(body));
            RequestLog requestLog = requestLogRepository.findById(id).get();
            requestLog.setRequestBody(GsonUtils.toJson(body));
            requestLogRepository.save(requestLog);
        }
    }
}
