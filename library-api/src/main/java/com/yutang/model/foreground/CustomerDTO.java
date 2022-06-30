package com.yutang.model.foreground;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 2:30 下午
 * 用于新增用户
 */

@Data
public class CustomerDTO {
    // 用户真实姓名
    private String cusName;
    // 账号
    private String cusNum;
    // 密码
    private String cusPass;
    // 电话
    private String phone;
    // 身份证
    private String identity;
    // 本站余额
    private BigDecimal balance;
    // 常用地址
    private String address;
}
