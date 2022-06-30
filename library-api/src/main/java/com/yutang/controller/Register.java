package com.yutang.controller;

import com.yutang.config.ResultData;
import com.yutang.model.RegisterDTO;
import com.yutang.service.action.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 3:37 下午
 */

@Api("注册")
@RestController
public class Register {

    @Autowired
    RegisterService service;

    @ApiOperation("用户组册")
    @PostMapping("/register")
    public ResultData register(@RequestBody RegisterDTO model) {
        return service.register(model);
    }
}
