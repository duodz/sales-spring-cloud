package com.duodz.sales.product.controller;

import com.duodz.sales.common.CommonResult;
import com.duodz.sales.common.model.product.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duodz
 * @date 2020/3/3 14:36
 */
@RefreshScope
@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${server.port}")
    private String port;

    // config server动态配置
    @Value("${docker_address}")
    private String docker_address;

    private static List<Product> productList = new ArrayList<>();

    @PostConstruct
    public void init() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("互联网轻量级框架");
        product1.setPrice(new BigDecimal("78.5"));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("iphone手机");
        product2.setPrice(new BigDecimal("5000"));
        productList.add(product1);
        productList.add(product2);
    }

    @GetMapping("/list")
    public CommonResult getList() {
        return CommonResult.success(productList);
    }

    @GetMapping("/get/{id}")
    public CommonResult getById(@PathVariable("id") Long id) {
        System.out.println("docker_address:" + docker_address);
        System.out.println("/product/get/" + id + ",  port:" + port);
        List<Product> products = productList.stream()
                .filter(product -> product.getId().equals(id))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(products)) {
            return CommonResult.success(products.get(0));
        }
        return CommonResult.failed("未找到指定产品");
    }


}
