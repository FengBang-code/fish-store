package com.yutang.model.foreground;

import lombok.Data;

/**
 * @Author fzhuoxing
 * @Time 2022/4/18 9:02 上午
 * 用户修改密码DTO
 */

@Data
public class RevisePassDTO {
    // 原密码
    String originalPass;

    // 密码
    String password;
}
