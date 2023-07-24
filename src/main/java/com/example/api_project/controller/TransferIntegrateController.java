package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.service.TransferIntegrateService;
import com.example.api_project.vo.TransferIntegrate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/transferIntegrate")
@CrossOrigin(origins = "*")

public class TransferIntegrateController {
    /**
     * 服务对象
     */
    @Autowired
    private TransferIntegrateService TransferIntegrateService;

    /**
     * 分页查询
     *
     * @param transferIntegrate 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(TransferIntegrate transferIntegrate, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.TransferIntegrateService.queryByPage(transferIntegrate, startRows,pageSize));
    }

}
