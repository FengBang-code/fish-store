package com.yutang.model.background;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author fzhuoxing
 * @Time 2022/6/30 7:38 下午
 * 环形图vo
 */

@Data
public class CirVO {
    // 书本类型
    private String type;
    // 书本类型对应的价格
    private BigDecimal bookPrice;
}
