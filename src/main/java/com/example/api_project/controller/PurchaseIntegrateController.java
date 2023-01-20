package com.example.api_project.controller;

import com.example.api_project.service.PurchaseIntegrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/purchaseIntegrate")
public class PurchaseIntegrateController {
    /**
     * 服务对象
     */
    @Autowired
    private PurchaseIntegrateService purchaseIntegrateService;

}
