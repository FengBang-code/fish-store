package com.yutang.model;

import lombok.Data;

/**
 * @Author fzhuoxing
 * @Time 2022/4/14 3:34 下午
 */

@Data
public class LoginDTO {
    // 账号
    private String userNum;
    // 密码
    private String password;
    // 0为顾客， 1为管理员
    private Integer state;
}
