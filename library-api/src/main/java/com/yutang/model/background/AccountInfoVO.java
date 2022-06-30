package com.yutang.model.background;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author fzhuoxing
 * @Time 2022/4/23 11:01 上午
 */

@Data
public class AccountInfoVO {
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
    // 订单创建时间
    private LocalDateTime createTime;
    // 订单备注
    private String orderRemark;
    // 购书名称
    private List<String> bookNames;
    // 购书数量
    private List<Integer> bookCount;
    // 订单状态
    private String orderState;

}
