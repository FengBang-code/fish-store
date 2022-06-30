package com.yutang.model;

import lombok.Data;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 3:33 下午
 * 注册DTO
 */

@Data
public class RegisterDTO {
    // 真实姓名
    private String cusName;
    // 用户账号
    private String cusNum;
    // 用户密码
    private String cusPass;
    // 手机号
    private String phone;
    // 身份证号
    private String identity;
    // 常用地址
    private String address;
}
