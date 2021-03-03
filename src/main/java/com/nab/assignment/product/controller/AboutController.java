package com.nab.assignment.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/about-us")
public class AboutController {
    @GetMapping(path = "/version")
    public String getVersion() {
        return "1.0.0";
    }
}
