package com.yutang.model.background;

import lombok.Data;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 10:52 上午
 */

@Data
public class CustomerUpdateDTO {
    // id
    private Long id;
    // 手机
    private String phone;
    // 常用地址
    private String address;
}
