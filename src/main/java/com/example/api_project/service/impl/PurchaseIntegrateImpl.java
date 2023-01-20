package com.example.api_project.service.impl;

import com.example.api_project.mapper.PurchaseIntegrateMapper;
import com.example.api_project.service.PurchaseIntegrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 销售统计
 *
 */
@Service("PurchaseIntegrateService")
public class PurchaseIntegrateImpl implements PurchaseIntegrateService {
    @Autowired
    private PurchaseIntegrateMapper purchaseIntegrateMapper;


}
