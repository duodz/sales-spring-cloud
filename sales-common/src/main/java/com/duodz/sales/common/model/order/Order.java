package com.duodz.sales.common.model.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author duodz
 * @date 2020/3/3 15:22
 */
@Data
@NoArgsConstructor
public class Order {
    private Long id;
    private String address;
    private BigDecimal total;

    private List<OrderItem> items;
}
