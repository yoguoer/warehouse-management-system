package com.example.api_project.controller;

import com.example.api_project.service.SalesIntegrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/salesIntegrate")
public class SalesIntegrateController {
    /**
     * 服务对象
     */
    @Autowired
    private SalesIntegrateService salesIntegrateService;

}
