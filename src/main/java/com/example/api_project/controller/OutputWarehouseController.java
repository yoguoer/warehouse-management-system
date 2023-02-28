package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.OutputWarehouse;
import com.example.api_project.pojo.ShopkeeperWarehouse;
import com.example.api_project.service.OutputWarehouseService;
import com.example.api_project.service.ShopkeeperWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * 出库表(OutputWarehouse)表控制层
 *
 */
@RestController
@RequestMapping("api/outputWarehouse")
@CrossOrigin(origins = "*")

public class OutputWarehouseController {
    /**
     * 服务对象
     */
    @Autowired
    private OutputWarehouseService outputWarehouseService;

    @Autowired
    private ShopkeeperWarehouseService shopkeeperWarehouseService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.outputWarehouseService.querylist());
    }

    /**
     * 分页查询
     *
     * @param outputWarehouse 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(OutputWarehouse outputWarehouse, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.outputWarehouseService.queryByPage(outputWarehouse, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param outputWarehouseKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(String outputWarehouseKey) {
        return ResponseData.success(this.outputWarehouseService.queryById(outputWarehouseKey));
    }

    /**
     * 新增数据
     *
     * @param outputWarehouse 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody OutputWarehouse outputWarehouse) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        outputWarehouse.setOutputWarehouseKey(System.currentTimeMillis() + String.valueOf(number));
        Integer isDeleted=0;
        outputWarehouse.setIsDeleted(isDeleted);
        OutputWarehouse result=this.outputWarehouseService.insert(outputWarehouse);

        //如果是新增客户订购单，需要更新一下占用数
        Integer status=outputWarehouse.getStatus();
        if(null!=status&&status==4){
            String shopCode=outputWarehouse.getShopCode();
            String goodsCode=outputWarehouse.getGoodsCode();
            ShopkeeperWarehouse shopkeeperWarehouse=shopkeeperWarehouseService.queryForKey(shopCode,goodsCode);//查询相关库存
            Integer occupyNum=shopkeeperWarehouse.getOccupyNum()+outputWarehouse.getOutputPlan();//新增占用数
            shopkeeperWarehouse.setOccupyNum(occupyNum);//设置新的占用数
            shopkeeperWarehouse.setAvailableNum(shopkeeperWarehouse.getAccountNum()+shopkeeperWarehouse.getOnwayNum()-occupyNum);//设置可用数
            this.shopkeeperWarehouseService.update(shopkeeperWarehouse);
        }
        return ResponseData.success(result);
    }

    /**
     * 编辑数据
     *
     * @param outputWarehouse 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody OutputWarehouse outputWarehouse) {
        Integer isDeleted=0;
        outputWarehouse.setIsDeleted(isDeleted);
        return ResponseData.success(this.outputWarehouseService.update(outputWarehouse));
    }

    /**
     * 删除数据
     *
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody OutputWarehouse outputWarehouse) {
        if(outputWarehouse.getIsDeleted()==1){
            return ResponseData.success(this.outputWarehouseService.deleteById(outputWarehouse));
        }else{
            String outputWarehouseKey=outputWarehouse.getOutputWarehouseKey();
            Integer isDeleted=this.outputWarehouseService.queryById(outputWarehouseKey).getIsDeleted();
            if(isDeleted==0){
                Integer newDeleted=1;
                outputWarehouse.setIsDeleted(newDeleted);
                this.outputWarehouseService.update(outputWarehouse);
            }else{
                this.outputWarehouseService.deleteById(outputWarehouse);
            }
        }
        return ResponseData.success();
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<OutputWarehouse> outputWarehouseKeys) {
        for(OutputWarehouse outputWarehouse: outputWarehouseKeys){
            if(outputWarehouse.getIsDeleted()==1){
                return ResponseData.success(this.outputWarehouseService.deleteById(outputWarehouse));
            }else{
                String outputWarehouseKey=outputWarehouse.getOutputWarehouseKey();
                Integer isDeleted=this.outputWarehouseService.queryById(outputWarehouseKey).getIsDeleted();
                if(isDeleted==0){
                    Integer newDeleted=1;
                    outputWarehouse.setIsDeleted(newDeleted);
                    return ResponseData.success(this.outputWarehouseService.update(outputWarehouse));
                }else{
                    return ResponseData.success(this.outputWarehouseService.deleteById(outputWarehouse));
                }
            }
        }
        return ResponseData.success();
    }
}

