package com.duodz.sales.common.model.order;

import com.duodz.sales.common.model.product.Product;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author duodz
 * @date 2020/3/3 15:23
 */
@Data
public class OrderItem {
    private Long id;
    private Long orderId;
    private Long productId;
    private BigDecimal price;
    private Integer count;
    private Product product;
}
