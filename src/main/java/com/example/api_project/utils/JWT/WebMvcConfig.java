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
                .addPathPatterns("/api/user/**")
                .addPathPatterns("/api/brand/**")
                .addPathPatterns("/api/vehicle/**")
                .addPathPatterns("/api/entity/**")
                .addPathPatterns("/api/category/**")

                .addPathPatterns("/api/contact/**")
                .addPathPatterns("/api/customer/**")

//                .addPathPatterns("/api/goods/**")

                .addPathPatterns("/api/inventory/**")
                .addPathPatterns("/api/district/**")
                .addPathPatterns("/api/position/**")

                .addPathPatterns("/api/supplier/**")
                .addPathPatterns("/api/supplierAddress/**")
                .addPathPatterns("/api/supplierBilling/**")
                .addPathPatterns("/api/SupplierInventory/**")
                .addPathPatterns("/api/ShopInventory/**")
                .addPathPatterns("/api/shopkeeperWarehouse/**")
                .addPathPatterns("/api/alertInfo/**")
                .addPathPatterns("/api/inputWarehouse/**")

        ;
    }
}

