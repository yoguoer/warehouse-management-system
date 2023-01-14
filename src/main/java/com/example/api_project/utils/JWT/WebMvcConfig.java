package com.example.api_project.utils.JWT;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//自定义配置类
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //设置拦截路径，排除路径，优先级等
        registry.addInterceptor(new TokenInterceptor())
                .excludePathPatterns("/api/login/**").order(11)
                .addPathPatterns("/api/user/**")//用户
                .addPathPatterns("/api/brand/**")//品牌
                .addPathPatterns("/api/vehicle/**")//车辆
                .addPathPatterns("/api/entity/**")//业务实体
                .addPathPatterns("/api/category/**")//分类
                .addPathPatterns("/api/contact/**")//联系人
                .addPathPatterns("/api/customer/**")//客户
                .addPathPatterns("/api/goods/**")//商品
                .addPathPatterns("/api/shop/**")//门店
                .addPathPatterns("/api/inventory/**")//仓库
                .addPathPatterns("/api/district/**")//区域
                .addPathPatterns("/api/position/**")//货位
                .addPathPatterns("/api/supplier/**")//供应商
                .addPathPatterns("/api/supplierAddress/**")//供应商地址
                .addPathPatterns("/api/supplierBilling/**")//供应商开票信息
                .addPathPatterns("/api/SupplierInventory/**")//供应商仓库
                .addPathPatterns("/api/ShopInventory/**")//门店仓库
                .addPathPatterns("/api/shopkeeperWarehouse/**")//门店仓库库存
                .addPathPatterns("/api/alertInfo/**")//预警信息
                .addPathPatterns("/api/inputWarehouse/**")//入库表
                .addPathPatterns("/api/outputWarehouse/**")//出库表
                .addPathPatterns("/api/returnCheck/**")//退货审批表
                .addPathPatterns("/api/transferCheck/**")//调货审批表
                .addPathPatterns("/api/detailWarehouse/**")//出入库明细表


        ;
    }
}

