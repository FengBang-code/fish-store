package com.yutang.model.background;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author fzhuoxing
 * @Time 2022/4/18 10:50 上午
 */

@Data
public class AccountQueryVO {
    // 流水id
    private Long id;
    // 流水号
    private String accountNum;
    // 订单号
    private String orderNum;
    // 客户名
    private String cusName;
    // 交易金额
    private BigDecimal accountAmount;
    // 流水备注
    private String accountRemark;
    // 支付类型
    private String payType;
    // 手机号
    private String phone;
}
