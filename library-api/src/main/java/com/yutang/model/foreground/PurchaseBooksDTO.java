package com.yutang.model.foreground;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author fzhuoxing
 * @Time 2022/4/18 10:10 上午
 * 下单DTO
 */

@Data
public class PurchaseBooksDTO {
    // 书本的ids
    private List<Long> ids;
    // 对应的数量
    private List<Integer> count;
    // 支付方式 0为本站支付，1为网银支付，2为现金支付
    private String payType;
    // 订单备注
    private String orderRemark;
    // 应付款金额
    private BigDecimal orderAmount;
    // 实际付款金额
    private BigDecimal realityAmount;
    // 下单客户的id
    private Long cusId;
}
