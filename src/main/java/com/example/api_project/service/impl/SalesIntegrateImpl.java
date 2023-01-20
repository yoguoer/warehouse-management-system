package com.example.api_project.service.impl;

import com.example.api_project.mapper.SalesIntegrateMapper;
import com.example.api_project.service.SalesIntegrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 销售统计
 *
 */
@Service("SalesIntegrateService")
public class SalesIntegrateImpl implements SalesIntegrateService {
    @Autowired
    private SalesIntegrateMapper salesIntegrateMapper;


}
