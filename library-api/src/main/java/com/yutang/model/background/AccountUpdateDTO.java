package com.yutang.model.background;

import lombok.Data;

/**
 * @Author fzhuoxing
 * @Time 2022/4/18 4:40 下午
 */

@Data
public class AccountUpdateDTO {
    // id
    private Long id;
    // 流水备注
    private String accountRemark;
    // 支付类型
    private String payType;
}
