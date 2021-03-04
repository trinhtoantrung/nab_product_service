package com.nab.assignment.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter @Setter
public class AppInfoDTO implements Serializable {
    @JsonProperty("app_name")
    private String appName;

    @JsonProperty("version")
    private String version;

    @JsonProperty("description")
    private String description;
}
