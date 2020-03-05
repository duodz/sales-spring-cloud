package com.duodz.sales.order.controller;

import com.duodz.sales.common.CommonResult;
import com.duodz.sales.common.model.order.Order;
import com.duodz.sales.common.model.order.OrderItem;
import com.duodz.sales.common.model.product.Product;
import com.duodz.sales.order.config.ProductClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duodz
 * @date 2020/3/3 15:21
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProductClient productClient;

    private static List<Order> orderList = new ArrayList<>();

    @PostConstruct
    public void init() {
        Order order = new Order();
        order.setId(1L);
        order.setAddress("xxxxxxxxxxxxxxxxx");
        order.setTotal(new BigDecimal("78.5"));

        OrderItem item = new OrderItem();
        item.setId(1L);
        item.setOrderId(1L);
        item.setProductId(1L);
        item.setPrice(new BigDecimal("78.5"));
        item.setCount(1);
        order.setItems(Arrays.asList(item));
        orderList.add(order);
    }

    @GetMapping("/get/{id}")
    // 微服务调用异常时，会降级处理到getOrderFail
    @HystrixCommand(fallbackMethod = "getOrderFail")
    public CommonResult getOrder(@PathVariable("id") Long id) {
        List<Order> orders = orderList.stream()
                .filter(order -> order.getId().equals(id))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(orders)) {
            return CommonResult.failed("未找到指定订单");
        }

        Order order = orders.get(0);
        for (OrderItem item : order.getItems()) {
            // restTemplate
            /*
            CommonResult<Product> commonResult = restTemplate.exchange("http://sales-product-service/product/get/" + item.getProductId(),
                    HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<Product>>() {
                    }).getBody();
            item.setProduct(commonResult.getData());
            */

            // feign
            CommonResult<Product> commonResult = productClient.getById(item.getProductId());
            item.setProduct(commonResult.getData());
            System.out.println(commonResult);
        }

        return CommonResult.success(order);
    }

    private CommonResult getOrderFail(Long id) {
        System.out.println("getOrderFail");
        return CommonResult.failed("服务异常");
    }

    @GetMapping("/test")
    public String test(){
        System.out.println("test");
        return "test";
    }


}
