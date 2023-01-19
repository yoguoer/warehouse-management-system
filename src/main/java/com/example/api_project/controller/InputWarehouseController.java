package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.InputWarehouse;
import com.example.api_project.pojo.ShopkeeperWarehouse;
import com.example.api_project.service.InputWarehouseService;
import com.example.api_project.service.ShopkeeperWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * 入库表(InputWarehouse)表控制层
 *
 */
@RestController
@RequestMapping("api/inputWarehouse")
public class InputWarehouseController {
    /**
     * 服务对象
     */
    @Autowired
    private InputWarehouseService inputWarehouseService;

    @Autowired
    private ShopkeeperWarehouseService shopkeeperWarehouseService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.inputWarehouseService.querylist());
    }

    /**
     * 分页查询
     *
     * @param inputWarehouse 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(InputWarehouse inputWarehouse, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.inputWarehouseService.queryByPage(inputWarehouse, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param inputWarehouseKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("inputWarehouseKey") String inputWarehouseKey) {
        return ResponseData.success(this.inputWarehouseService.queryById(inputWarehouseKey));
    }

    /**
     * 新增数据
     *
     * @param inputWarehouse 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody InputWarehouse inputWarehouse) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        inputWarehouse.setInputWarehouseKey(System.currentTimeMillis() + String.valueOf(number));
        Integer isDeleted=0;
        inputWarehouse.setIsDeleted(isDeleted);
        return ResponseData.success(this.inputWarehouseService.insert(inputWarehouse));
    }

    /**
     * 编辑数据
     *
     * @param inputWarehouse 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody  InputWarehouse inputWarehouse) {
        Integer isDeleted=0;
        inputWarehouse.setIsDeleted(isDeleted);
        InputWarehouse result=this.inputWarehouseService.update(inputWarehouse);

        //如果是供应商发货了，需要更新一下在途数
        Integer status=inputWarehouse.getStatus();
        if(null!=status&&status==2){
            String shopCode=inputWarehouse.getShopCode();
            String goodsCode=inputWarehouse.getGoodsCode();
            ShopkeeperWarehouse shopkeeperWarehouse=shopkeeperWarehouseService.queryForKey(shopCode,goodsCode);//查询相关库存
            Integer onWayNum=shopkeeperWarehouse.getOnwayNum()+inputWarehouse.getInputActual();//新增在途数
            shopkeeperWarehouse.setOnwayNum(onWayNum);//设置新的在途数
            shopkeeperWarehouse.setAvailableNum(shopkeeperWarehouse.getAccountNum()+onWayNum-shopkeeperWarehouse.getOccupyNum());//设置可用数
            this.shopkeeperWarehouseService.update(shopkeeperWarehouse);
        }
        return ResponseData.success(result);
    }

    /**
     * 删除数据
     *
     * @param inputWarehouse
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody InputWarehouse inputWarehouse) {
        if(inputWarehouse.getIsDeleted()==1){
            return ResponseData.success(this.inputWarehouseService.deleteById(inputWarehouse));
        }else{
            String inputWarehouseKey=inputWarehouse.getInputWarehouseKey();
            Integer isDeleted=this.inputWarehouseService.queryById(inputWarehouseKey).getIsDeleted();
            if(isDeleted==0){
                Integer newDeleted=1;
                inputWarehouse.setIsDeleted(newDeleted);
                return ResponseData.success(this.inputWarehouseService.update(inputWarehouse));
            }else{
                return ResponseData.success(this.inputWarehouseService.deleteById(inputWarehouse));
            }
        }
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<InputWarehouse> inputWarehouseKeys) {
        for(InputWarehouse inputWarehouse: inputWarehouseKeys){
            if(inputWarehouse.getIsDeleted()==1){
                return ResponseData.success(this.inputWarehouseService.deleteById(inputWarehouse));
            }else{
                String inputWarehouseKey=inputWarehouse.getInputWarehouseKey();
                Integer isDeleted=this.inputWarehouseService.queryById(inputWarehouseKey).getIsDeleted();
                if(isDeleted==0){
                    Integer newDeleted=1;
                    inputWarehouse.setIsDeleted(newDeleted);
                    this.inputWarehouseService.update(inputWarehouse);
                }else{
                    this.inputWarehouseService.deleteById(inputWarehouse);
                }
            }
        }
        return ResponseData.success();
    }
}

