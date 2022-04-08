package com.practicing.crudforfun.controllers;

import com.practicing.crudforfun.utils.GetClientIp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HealthCheckController {

    @GetMapping("/get-ip")
    public String getIp(HttpServletRequest request) {
        return new GetClientIp().run(request);
    }

}
