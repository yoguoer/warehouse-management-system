package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.AlertInfo;
import com.example.api_project.pojo.ShopkeeperWarehouse;
import com.example.api_project.service.AlertInfoService;
import com.example.api_project.service.ShopkeeperWarehouseService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 预警信息表(AlertInfo)表控制层
 *
 */
@RestController
@RequestMapping("api/alertInfo")
public class AlertInfoController {
    /**
     * 服务对象
     */
    @Autowired
    private AlertInfoService alertInfoService;

    @Autowired
    private ShopkeeperWarehouseService shopkeeperWarehouseService;

    /**
     * 分页查询
     *
     * @param alertInfo 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(AlertInfo alertInfo, Integer page,Integer size)throws Exception {
        List<ShopkeeperWarehouse> swList= this.shopkeeperWarehouseService.querylist();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        for(ShopkeeperWarehouse item : swList){
            // 当前时间-最后操作时间>7天 连续未动销预警
            if(Math.ceil(Math.abs(df.parse(date).getTime() - df.parse(item.getOperateTime()).getTime()) / (1000 * 60 * 60 * 24))>=7){
                AlertInfo AIF = new AlertInfo();
                Random random = new Random();
                Integer number = random.nextInt(9000) + 1000;
                AIF.setAlertInfoKey(System.currentTimeMillis() + String.valueOf(number));
                AIF.setAlertTime(date);
                AIF.setGoodsCode(item.getGoodsCode());
                AIF.setShopSupplierCode(item.getShopCode());
                AIF.setStatus(0);//状态（0：未处理，1：已处理）
                AIF.setType(0);//预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警）
                if(null==this.alertInfoService.queryByMany(AIF)){
                    this.add(AIF);
                }
            }else{//已处理
                AlertInfo AIF3 = new AlertInfo();
                AIF3.setGoodsCode(item.getGoodsCode());
                AIF3.setShopSupplierCode(item.getShopCode());
                AIF3.setStatus(0);//状态（0：未处理，1：已处理）
                AIF3.setType(0);//预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警）
                AlertInfo temp3=this.alertInfoService.queryByMany(AIF3);
                if(null!=temp3){
                    temp3.setStatus(1);
                    this.edit(temp3);
                }
            }

            // 现存<=最低，缺货预警
            if(item.getAccountNum()<=item.getMinNum()){
                AlertInfo AIF = new AlertInfo();
                Random random = new Random();
                Integer number = random.nextInt(9000) + 1000;
                AIF.setAlertInfoKey(System.currentTimeMillis() + String.valueOf(number));
                AIF.setAlertTime(date);
                AIF.setGoodsCode(item.getGoodsCode());
                AIF.setShopSupplierCode(item.getShopCode());
                AIF.setStatus(0);//状态（0：未处理，1：已处理）
                AIF.setType(2);//预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警）
                if(null==this.alertInfoService.queryByMany(AIF)){
                    this.add(AIF);
                }
            }else{//已处理
                AlertInfo AIF1 = new AlertInfo();
                AIF1.setGoodsCode(item.getGoodsCode());
                AIF1.setShopSupplierCode(item.getShopCode());
                AIF1.setStatus(0);//状态（0：未处理，1：已处理）
                AIF1.setType(2);//预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警）
                AlertInfo temp1=this.alertInfoService.queryByMany(AIF1);
                if(null!=temp1){
                    temp1.setStatus(1);
                    this.edit(temp1);
                }
            }

            // 现存>最高，满仓预警
            if(item.getAccountNum()>=item.getMaxNum()){
                AlertInfo AIF = new AlertInfo();
                Random random = new Random();
                Integer number = random.nextInt(9000) + 1000;
                AIF.setAlertInfoKey(System.currentTimeMillis() + String.valueOf(number));
                AIF.setAlertTime(date);
                AIF.setGoodsCode(item.getGoodsCode());
                AIF.setShopSupplierCode(item.getShopCode());
                AIF.setStatus(0);//状态（0：未处理，1：已处理）
                AIF.setType(1);//预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警）
                if(null==this.alertInfoService.queryByMany(AIF)){
                    this.add(AIF);
                }
            }else{//已处理
                AlertInfo AIF2 = new AlertInfo();
                AIF2.setGoodsCode(item.getGoodsCode());
                AIF2.setShopSupplierCode(item.getShopCode());
                AIF2.setStatus(0);//状态（0：未处理，1：已处理）
                AIF2.setType(1);//预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警）
                AlertInfo temp2=this.alertInfoService.queryByMany(AIF2);
                if(null!=temp2){
                    temp2.setStatus(1);
                    this.edit(temp2);
                }
            }
        }
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.alertInfoService.queryByPage(alertInfo, startRows,pageSize));
    }

    /**
     * 通过预警对象、商品、类型、状态来判断是否存在过
     *
     * @param alertInfo
     * @return 单条数据
     */
    @GetMapping("/getByMany")
    public Result queryByMany(AlertInfo alertInfo) {
        return ResponseData.success(this.alertInfoService.queryByMany(alertInfo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("id") String id) {
        return ResponseData.success(this.alertInfoService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param alertInfo 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody AlertInfo alertInfo) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        alertInfo.setAlertInfoKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.alertInfoService.insert(alertInfo));
    }

    /**
     * 编辑数据
     *
     * @param alertInfo 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody AlertInfo alertInfo) {
        return ResponseData.success(this.alertInfoService.update(alertInfo));
    }

    /**
     * 删除数据
     *
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody AlertInfo alertInfo) {
        return ResponseData.success(this.alertInfoService.deleteById(alertInfo));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<AlertInfo> alertInfoKeys) {
        for(AlertInfo alertInfo: alertInfoKeys){
            this.alertInfoService.deleteById(alertInfo);
        }
        return ResponseData.success();
    }
}

