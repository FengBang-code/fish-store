package com.yutang.model.background;

import com.yutang.model.common.PageDTO;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author fzhuoxing
 * @Time 2022/4/18 10:26 上午
 * 流水记录分页查询dto
 */

@Data
public class AccountQueryDTO extends PageDTO implements Serializable {
    // 流水号
    private String accountNum;
    // 订单号
    private String orderNum;
    // 客户名
    private String cusName;
    // 手机号
    private String phone;
    // 支付类型
    private String payType;
    // 传递流水搜素开始时间
    private LocalDateTime begin;
    // 传递流水搜索结束时间
    private LocalDateTime end;
}
