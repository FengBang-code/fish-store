package com.yutang.model.background;

import lombok.Data;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 10:59 上午
 * 后台新增用户dto
 */

@Data
public class CustomerAddDTO {
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
}
