package com.yutang.service.action;

import com.yutang.config.ResultData;
import com.yutang.config.mapstruct.CustomerStruct;
import com.yutang.entity.Customer;
import com.yutang.model.RegisterDTO;
import com.yutang.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 3:38 下午
 * 注册Service
 */

@Service
public class RegisterService {
    @Autowired
    CustomerService customerService;

    /**
     * 注册用户
     * @param model
     * @return
     */
    public ResultData register(RegisterDTO model) {
        Customer cus = CustomerStruct.INSTANCES.toCustomerFromRegister(model);
        customerService.save(cus);
        return ResultData.succMsg("操作成功！");
    }
}
