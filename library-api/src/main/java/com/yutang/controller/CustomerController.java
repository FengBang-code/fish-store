package com.yutang.controller;

import com.yutang.config.ResultData;
import com.yutang.model.foreground.CustomerBookDTO;
import com.yutang.model.foreground.CustomerDTO;
import com.yutang.model.foreground.PurchaseBooksDTO;
import com.yutang.model.foreground.RevisePassDTO;
import com.yutang.service.action.Customer_Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 2:27 下午
 */

@Api("用户Controller")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    Customer_Service customerService;

    @ApiOperation("新增用户")
    @PostMapping("/add")
    public ResultData addCustomer(@RequestBody CustomerDTO model) {
        return customerService.addCustomer(model);
    }

    @ApiOperation("查询书本")
    @PostMapping("/getBooks")
    public ResultData bookQuery(@RequestBody CustomerBookDTO model) {
        return customerService.getBooks(model);
    }

    @ApiOperation("个人中心")
    @GetMapping("/personal")
    public ResultData personal(HttpServletRequest req) {
        return customerService.personal(req);
    }

    @ApiOperation("个人订单详细")
    @PostMapping("/ownOrders")
    public ResultData ownOrders() {
        return null;
    }

    @ApiOperation("修改密码")
    @PostMapping("/revisePassword")
    public ResultData revisePassword(@RequestBody RevisePassDTO model,HttpServletRequest req) {
        return customerService.revisePassword(req, model);
    }

    @ApiOperation("下单")
    @PostMapping("/purchase")
    public ResultData purchaseBooks(HttpServletRequest req,@RequestBody PurchaseBooksDTO model) throws Exception {
        return customerService.purchaseBooks(req, model);
    }
}
