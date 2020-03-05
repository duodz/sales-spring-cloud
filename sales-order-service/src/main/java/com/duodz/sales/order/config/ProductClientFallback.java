package com.duodz.sales.order.config;

import com.duodz.sales.common.CommonResult;
import com.duodz.sales.common.model.product.Product;
import org.springframework.stereotype.Component;

/**
 * @author duodz
 * @date 2020/3/3 16:39
 */
@Component
public class ProductClientFallback implements ProductClient {
    @Override
    public CommonResult<Product> getById(Long id) {
        System.out.println("ProductClientFallback");
        return CommonResult.failed("服务异常");
    }
}
