package com.nab.assignment.product.controller;

import com.nab.assignment.product.dto.AppInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/about-us")
public class AboutController {
    @Value("${application.name}")
    String name;

    @Value("${application.version}")
    String version;

    @Value("${application.description}")
    String description;


    @GetMapping("/info")
    public AppInfoDTO getInfo() {
        return new AppInfoDTO(name, version, description);
    }
}
