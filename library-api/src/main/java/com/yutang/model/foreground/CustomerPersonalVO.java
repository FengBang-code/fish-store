package com.yutang.model.foreground;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author fzhuoxing
 * @Time 2022/4/18 9:13 上午
 * 用户个人中心
 */

@Data
public class CustomerPersonalVO {
    // id
    private Long id;
    // 真实姓名
    private String cusName;
    // 账号
    private String cusNum;
    // 手机号
    private String phone;
    // 身份证
    private String identity;
    // 本站余额
    private BigDecimal balance;
    // 常用地址
    private String address;
}
