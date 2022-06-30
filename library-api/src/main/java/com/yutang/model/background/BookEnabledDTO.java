package com.yutang.model.background;

import lombok.Data;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 9:11 上午
 * 图书是否启用DTO
 */

@Data
public class BookEnabledDTO {
    // id
    private Long id;
    // 启用状态
    private Boolean enabled;
    // 不启用原因
    private String notEnabledReason;
}
