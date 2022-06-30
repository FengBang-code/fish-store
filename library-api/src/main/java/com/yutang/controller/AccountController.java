package com.yutang.controller;

import com.yutang.config.ResultData;
import com.yutang.model.background.AccountQueryDTO;
import com.yutang.model.background.AccountUpdateDTO;
import com.yutang.model.background.BaseId;
import com.yutang.service.action.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 4:03 下午
 */

@Api("流水controller")
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @ApiOperation("流水记录查询")
    @PostMapping("/query")
    public ResultData queryAccount(@RequestBody AccountQueryDTO model) {
        return accountService.queryAccount(model);
    }

    @ApiOperation("编辑流水")
    @PostMapping("/update")
    public ResultData updateAccount(@RequestBody AccountUpdateDTO model) {
        return accountService.updateAccount(model);
    }

    @ApiOperation("流水详情")
    @PostMapping("/info")
    public ResultData AccountInfo(@RequestBody BaseId model) {
        return accountService.AccountInfo(model);
    }

}
