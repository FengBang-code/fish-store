package com.yutang.model.background;

import lombok.Data;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 2:58 下午
 */

@Data
public class CustomerAddRes {
    // id
    private Long id;
    // 用户真实姓名
    private String cusName;
    // 用户账号
    private String cusNum;
    // 手机
    private String phone;
    // 身份证
    private String identity;
    // 常用地址
    private String address;
    // 密码
    private String cusPass;
}
