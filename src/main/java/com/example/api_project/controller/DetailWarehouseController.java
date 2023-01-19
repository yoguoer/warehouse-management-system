package com.example.api_project.controller;

import com.example.api_project.pojo.DetailWarehouse;
import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.ShopkeeperWarehouse;
import com.example.api_project.service.DetailWarehouseService;
import com.example.api_project.service.ShopkeeperWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * 出入库明细表(DetailWarehouse)表控制层
 *
 */
@RestController
@RequestMapping("api/detailWarehouse")
public class DetailWarehouseController {
    /**
     * 服务对象
     */
    @Autowired
    private DetailWarehouseService detailWarehouseService;
    @Autowired
    private ShopkeeperWarehouseService shopkeeperWarehouseService;
    /**
     * 分页查询
     *
     * @param detailWarehouse 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(DetailWarehouse detailWarehouse, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.detailWarehouseService.queryByPage(detailWarehouse, startRows,pageSize));
    }

    /**
     * 通过商品、门店、单号查询key
     *
     */
    @GetMapping("/getForKey")
    public Result getForKey(DetailWarehouse detailWarehouse) {
        return ResponseData.success(this.detailWarehouseService.getForKey(detailWarehouse));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param detailWarehouseKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("detailWarehouseKey") String detailWarehouseKey) {
        return ResponseData.success(this.detailWarehouseService.queryById(detailWarehouseKey));
    }

    /**
     * 新增数据
     *
     * @param detailWarehouse 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody DetailWarehouse detailWarehouse) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        detailWarehouse.setDetailWarehouseKey(System.currentTimeMillis() + String.valueOf(number));
        ShopkeeperWarehouse shopkeeperWarehouse;
        //不存在ShopkeeperWarehouseKey，需要初始化设置一下
        if(null==detailWarehouse.getShopkeeperWarehouseKey()||""==detailWarehouse.getShopkeeperWarehouseKey()){
            String shopCode=detailWarehouse.getShopCode();
            String goodsCode=detailWarehouse.getGoodsCode();
            shopkeeperWarehouse=shopkeeperWarehouseService.queryForKey(shopCode,goodsCode);
            detailWarehouse.setShopkeeperWarehouseKey(shopkeeperWarehouse.getShopkeeperWarehouseKey());
            detailWarehouse.setStartNum(shopkeeperWarehouse.getAccountNum());
        }else {//已经存在，直接通过ShopkeeperWarehouseKey来查
            shopkeeperWarehouse = shopkeeperWarehouseService.queryById(detailWarehouse.getShopkeeperWarehouseKey());
            detailWarehouse.setStartNum(shopkeeperWarehouse.getAccountNum());
        }

        //交易类型(0采购入库、1采购退货出库、2零售出库、3零售退货入库、4客户订购出库、5客户订购退货入库、6调货入库、7调货出库、8盘盈入库、9盘亏出库)
        Integer transType=detailWarehouse.getTransType();
        if(transType==0||transType==3||transType==5||transType==6||transType==8){//采购入库
            detailWarehouse.setFinalNum(shopkeeperWarehouse.getAccountNum()+detailWarehouse.getQuantity());
        }else if(transType==1||transType==2||transType==4||transType==7||transType==9){//销售出库
            detailWarehouse.setFinalNum(shopkeeperWarehouse.getAccountNum()-detailWarehouse.getQuantity());
        }

        Integer status=detailWarehouse.getStatus();
        DetailWarehouse result=this.detailWarehouseService.insert(detailWarehouse);

        //'0' ? '在单'， '1' ? '生产'，'2' ? '在途'，'3' ? '入库'， '4' ? '占用'，'5' ? '出库'
        //(0采购入库6调货入库)
        if(null!=status && status==2 && (transType==0||transType==6)){//原始状态为在途，确认收货入库（采购、调货）
            shopkeeperWarehouse.setOperateTime(result.getAtTime());
            shopkeeperWarehouse.setAccountNum(result.getFinalNum());//最终库存量
            Integer onWayNum=shopkeeperWarehouse.getOnwayNum()-result.getQuantity();//在途数减少
            shopkeeperWarehouse.setOnwayNum(shopkeeperWarehouse.getOnwayNum()-result.getQuantity());
            shopkeeperWarehouse.setAvailableNum(result.getFinalNum()+onWayNum-shopkeeperWarehouse.getOccupyNum());
            //(1采购退货出库)
        }else if(null!=status && status==3 && (transType==1)) {//原始状态为入库（退货出库）
            shopkeeperWarehouse.setOperateTime(result.getAtTime());
            shopkeeperWarehouse.setAccountNum(result.getFinalNum());//最终库存量
            shopkeeperWarehouse.setAvailableNum(result.getFinalNum()+shopkeeperWarehouse.getOnwayNum()-shopkeeperWarehouse.getOccupyNum());
            //(4客户订购出库)
        }else if(null!=status && status==4 && (transType==4)) {//原始状态为占用（订购）
            shopkeeperWarehouse.setOperateTime(result.getAtTime());
            shopkeeperWarehouse.setAccountNum(result.getFinalNum());//最终库存量
            Integer occupyNum=shopkeeperWarehouse.getOccupyNum()-result.getQuantity();//占用数减少
            shopkeeperWarehouse.setOccupyNum(occupyNum);
            shopkeeperWarehouse.setAvailableNum(result.getFinalNum()+shopkeeperWarehouse.getOnwayNum()-occupyNum);
            //(3零售退货入库、5客户订购退货入库)
        }else if(null!=status && status==5 && (transType==3||transType==5)) {//原始状态为出库（退货入库），直接加减就行了
            shopkeeperWarehouse.setOperateTime(result.getAtTime());
            shopkeeperWarehouse.setAccountNum(result.getFinalNum());//最终库存量
            shopkeeperWarehouse.setAvailableNum(result.getFinalNum()+shopkeeperWarehouse.getOnwayNum()-shopkeeperWarehouse.getOccupyNum());
        }
        //(2零售出库、7调货出库)
        else if(transType==2||transType==7||transType==8||transType==9) {//零售、调货出库、盘盈盘亏比较特殊，直接加减就行了
            shopkeeperWarehouse.setOperateTime(result.getAtTime());
            shopkeeperWarehouse.setAccountNum(result.getFinalNum());//最终库存量
            shopkeeperWarehouse.setAvailableNum(result.getFinalNum()+shopkeeperWarehouse.getOnwayNum()-shopkeeperWarehouse.getOccupyNum());
        }

        this.shopkeeperWarehouseService.update(shopkeeperWarehouse);
        return ResponseData.success(this.queryById(result.getDetailWarehouseKey()));
    }

    /**
     * 编辑数据
     *
     * @param detailWarehouse 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody DetailWarehouse detailWarehouse) {
        String detailWarehouseKey=this.detailWarehouseService.getForKey(detailWarehouse).getDetailWarehouseKey();
        detailWarehouse.setDetailWarehouseKey(detailWarehouseKey);
        return ResponseData.success(this.detailWarehouseService.update(detailWarehouse));
    }

    /**
     * 删除数据
     *
     * @param detailWarehouse
     * @return 删除是否成功
     */
    @PostMapping ("/delete")
    public Result deleteById(@RequestBody DetailWarehouse detailWarehouse) {
        return ResponseData.success(this.detailWarehouseService.deleteById(detailWarehouse));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<DetailWarehouse> detailWarehouseKeys) {
        for(DetailWarehouse detailWarehouse: detailWarehouseKeys){
            this.detailWarehouseService.deleteById(detailWarehouse);
        }
        return ResponseData.success();
    }
}

