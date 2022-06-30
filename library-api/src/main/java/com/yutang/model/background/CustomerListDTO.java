package com.yutang.model.background;

import com.yutang.model.common.PageDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 4:55 下午
 * 客户列表dto
 */

@Data
public class CustomerListDTO extends PageDTO implements Serializable {

    // 客户真实名字
    private String cusName;
    // 账号
    private String cusNum;
    // 手机号
    private String phone;
}
