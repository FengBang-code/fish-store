package com.yutang.controller;

import com.yutang.config.ResultData;
import com.yutang.service.action.Order_Service;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 4:03 下午
 */

@Api("订单controller")
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    Order_Service orderService;

    @GetMapping("/getRecord")
    public ResultData getRecord() {
        return ResultData.succMsg(orderService.getRecord());
    }
}
