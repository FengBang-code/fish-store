package com.yutang.controller;

import com.yutang.config.ResultData;
import com.yutang.model.background.*;
import com.yutang.service.action.AccountService;
import com.yutang.service.action.Employee_Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 2:49 下午
 */

@Api("员工controller")
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    Employee_Service employeeService;
    @Autowired
    AccountService accountService;

    @ApiOperation("新增员工")
    @PostMapping("/add")
    public ResultData addEmp(@RequestBody EmployeeDTO model) {
        return employeeService.addEmployee(model);
    }

    //***********************图书***********************//
    @ApiOperation("查看图书列表")
    @PostMapping("/getBooks")
    public ResultData getBooks(@RequestBody EmployeeBookDTO model) {
        return employeeService.getBooks(model);
    }

    @ApiOperation("图书启用操作")
    @PostMapping("/isEnabled")
    public ResultData isEnabled(@RequestBody BookEnabledDTO model) {
        return employeeService.isEnabled(model);
    }

    @ApiOperation("图书更新")
    @PostMapping("/bookUpdate")
    public ResultData bookUpdate(@RequestBody BookUpdateDTO model) {
        return employeeService.bookUpdate(model);
    }

    @ApiOperation("图书详细")
    @PostMapping("/bookInfo")
    public ResultData bookInfo(@RequestBody BaseId model) {
        return employeeService.bookInfo(model);
    }

    @ApiOperation("图书移除")
    @PostMapping("/bookRemove")
    public ResultData bookRemove(@RequestBody BaseId model) {
        return employeeService.bookRemove(model);
    }

    @ApiOperation("图书添加")
    @PostMapping("/bookAdd")
    public ResultData bookAdd(@RequestBody BookAddDTO model) {
        return employeeService.bookAdd(model);
    }
    //***********************客户***********************//
    @ApiOperation("查看客户列表")
    @PostMapping("/getCustomers")
    public ResultData getCustomers(@RequestBody CustomerListDTO model) {
        return employeeService.getCustomers(model);
    }

    @ApiOperation("查看客户详情")
    @PostMapping("/customerInfo")
    public ResultData customersInfo(@RequestBody BaseId model) {
        return employeeService.customersInfo(model);
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/updateCustomer")
    public ResultData updateCustomer(@RequestBody CustomerUpdateDTO model) {
        return employeeService.updateCustomer(model);
    }

    @ApiOperation("逻辑删除用户")
    @PostMapping("/removeCustomer")
    public ResultData removeCustomer(@RequestBody BaseId model) {
        return employeeService.removeCustomer(model);
    }

    @ApiOperation("新增用户")
    @PostMapping("/addCustomer")
    public ResultData addCustomer(@RequestBody CustomerAddDTO model) {
        return employeeService.addCustomer(model);
    }

    @ApiOperation("初始化密码")
    @PostMapping("/initCustomerPass")
    public ResultData updateCustomerPass(@RequestBody BaseIds model) {
        return employeeService.updateCustomerPass(model);
    }

    @ApiOperation("退款")
    @PostMapping("/refund")
    public ResultData refund(HttpServletRequest req,@RequestBody RefundDTO model) throws Exception {
        return accountService.refund(req, model);
    }
}
