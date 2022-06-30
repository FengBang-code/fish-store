package com.yutang.controller;

import com.yutang.config.ResultData;
import com.yutang.entity.Customer;
import com.yutang.model.LoginDTO;
import com.yutang.service.action.Customer_Service;
import com.yutang.service.action.LoginService;
import com.yutang.service.action.LogoutService;
import com.yutang.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author fzhuoxing
 * @Time 2022/4/14 2:49 下午
 * 登录，登出
 */
@Api("登录登出")
@RestController
public class Login {
    @Autowired
    LoginService loginService;

    @Autowired
    LogoutService logoutService;

    @Autowired
    Customer_Service customerService;

    @Autowired
    JWTUtils jwtUtils;

    /**
     * 登录接口
     * @param model
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public ResultData login(@RequestBody LoginDTO model, HttpServletResponse res) throws IOException {
        return loginService.checkUser(model, res);
    }

    @ApiOperation("权限")
    @GetMapping("/getAuthorization")
    public ResultData getAuth(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        String subject = jwtUtils.getClaimByToken(token).getSubject();
        String[] s = subject.split("_");
        String authorization = s[0];
        return ResultData.succMsg(authorization);
    }

}
