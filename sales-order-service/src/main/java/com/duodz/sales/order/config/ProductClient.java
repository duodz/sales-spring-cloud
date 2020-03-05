package com.duodz.sales.order.config;

import com.duodz.sales.common.CommonResult;
import com.duodz.sales.common.model.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author duodz
 * @date 2020/3/3 16:19
 */
@FeignClient(value = "sales-product-service",fallback = ProductClientFallback.class)
public interface ProductClient {

    @GetMapping("/product/get/{id}")
    CommonResult<Product> getById(@PathVariable("id")Long id);
}
