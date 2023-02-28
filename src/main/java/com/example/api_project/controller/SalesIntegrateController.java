package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.service.SalesIntegrateService;
import com.example.api_project.vo.SalesIntegrate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/salesIntegrate")
@CrossOrigin(origins = "*")

public class SalesIntegrateController {
    /**
     * 服务对象
     */
    @Autowired
    private SalesIntegrateService salesIntegrateService;

    /**
     * 分页查询
     *
     * @param salesIntegrate 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(SalesIntegrate salesIntegrate, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.salesIntegrateService.queryByPage(salesIntegrate, startRows,pageSize));
    }

}
