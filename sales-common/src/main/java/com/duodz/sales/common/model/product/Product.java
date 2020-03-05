package com.duodz.sales.common.model.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author duodz
 * @date 2020/3/3 14:50
 */
@Data
@NoArgsConstructor
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}
