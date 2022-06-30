package com.yutang.model.background;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 9:28 上午
 * 书本更新DTO
 */

@Data
public class BookUpdateDTO {
    // id
    private Long id;
    // 库存
    private Integer stock;
    // 价格
    private BigDecimal price;
}
