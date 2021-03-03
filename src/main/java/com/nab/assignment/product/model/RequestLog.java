package com.nab.assignment.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "request_log")
public class RequestLog extends AbstractAuditingEntity {
    @Id
    private UUID id;

    private String path;

    @Column(length = 5000)
    private String queries;

    @Column(name = "request_headers", length = 5000)
    private String requestHeaders;

    @Column(name = "request_body", length = 5000)
    private String requestBody;

    private String method;

    public void setRequestBody(String requestBody) {
        if (requestBody != null) {
            this.requestBody = requestBody.length() > 5000 ? requestBody.substring(0, 5000) : requestBody;
        }
    }

    public void setRequestHeaders(String requestHeaders) {
        if (requestHeaders != null) {
            this.requestHeaders = requestHeaders.length() > 5000 ? requestHeaders.substring(0, 5000) : requestHeaders;
        }
    }

    public void setQueries(String queries) {
        if (queries != null) {
            this.queries = queries.length() > 5000 ? queries.substring(0, 5000) : queries;
        }
    }
}
