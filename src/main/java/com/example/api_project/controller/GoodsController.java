package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.Goods;
import com.example.api_project.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * 商品表(Goods)表控制层
 *
 */
@RestController
@RequestMapping("api/goods")
public class GoodsController {
    /**
     * 服务对象
     */
    @Autowired
    private GoodsService goodsService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.goodsService.querylist());
    }

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(Goods goods, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.goodsService.queryByPage(goods, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param goodsKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("goodsKey") String goodsKey) {
        return ResponseData.success(this.goodsService.queryById(goodsKey));
    }

    /**
     * 新增数据
     *
     * @param goods 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Goods goods) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        goods.setGoodsKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.goodsService.insert(goods));
    }

    /**
     * 编辑数据
     *
     * @param goods 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody Goods goods) {
        return ResponseData.success(this.goodsService.update(goods));
    }

    /**
     * 删除数据
     *
     * @return 删除是否成功
     */
    @PostMapping ("/delete")
    public Result deleteById(@RequestBody Goods goods) {
        if(this.goodsService.deleteById(goods)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.goodsService.deleteById(goods));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<Goods> goodsKeys) {
        for(Goods goods: goodsKeys){
            this.goodsService.deleteById(goods);
        }
        return ResponseData.success();
    }
}

