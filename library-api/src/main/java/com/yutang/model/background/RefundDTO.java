package com.yutang.model.background;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author fzhuoxing
 * @Time 2022/6/18 3:15 下午
 * 退款DTO
 */

@Data
public class RefundDTO {
    // 流水备注
    private String accountRemark;
    // 金额
    private BigDecimal accountAmount;
    // 客户名
    private String cusName;
    // 当前操作的账号
    private String cusNum;
    // 支付类型
    private String payType;
    // 订单号
    private String orderNum;
}
