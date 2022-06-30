package com.yutang.service.action;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yutang.config.ResultData;
import com.yutang.entity.Customer;
import com.yutang.entity.Employee;
import com.yutang.mapper.CustomerMapper;
import com.yutang.mapper.EmployeeMapper;
import com.yutang.model.LoginDTO;
import com.yutang.service.CustomerService;
import com.yutang.service.EmployeeService;
import com.yutang.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @Author fzhuoxing
 * @Time 2022/4/14 3:43 下午
 * 登录
 */

@Service
public class LoginService {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    CustomerService customerService;
    @Resource
    EmployeeMapper employeeMapper;
    @Resource
    CustomerMapper customerMapper;
    @Resource
    JWTUtils jwtUtils;

    /**
     * 登录接口，检测登录者，并且返回jwt
     * @param model
     * @param res
     * @return
     * @throws IOException
     */
    public ResultData checkUser(LoginDTO model, HttpServletResponse res) throws IOException {
        // 表示是管理员，还是客户
        String flag = "";
        // 管理员登录
        if(model.getState() == 1) {
            QueryWrapper wrapper = (QueryWrapper) new QueryWrapper().eq("emp_num",model.getUserNum());
            Employee emp = employeeService.getOne(wrapper);
            if(Objects.isNull(emp) || !emp.getEmpPass().equals(model.getPassword()))
                return ResultData.failMsg("账号密码不正确，请重新输入!");
        }
        // 用户登录
        else if(model.getState() == 0) {
            QueryWrapper wrapper = (QueryWrapper) new QueryWrapper().eq("cus_num",model.getUserNum());
            Customer cus = customerService.getOne(wrapper);
            if(Objects.isNull(cus) || !cus.getCusPass().equals(model.getPassword()))
                return ResultData.failMsg("账号密码不正确，请重新输入!");
        }
        // 设置最后登录时间
        if(model.getState() == 1) {
            flag = "manager_" + model.getUserNum();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            employeeMapper.updateLastLogin(dtf.format(LocalDateTime.now()), model.getUserNum());
        }
        else if(model.getState() == 0) {
            flag = "customer_" + model.getUserNum();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            customerMapper.updateLastLogin(dtf.format(LocalDateTime.now()), model.getUserNum());
        } else {
            return ResultData.failMsg("传递参数错误，登录失败！");
        }
        // 登录成功后返回jwt,写入到请求头中
        res.setContentType("application/json;charset=UTF-8");
        String jwt = jwtUtils.generateToken(flag);
        res.setHeader(jwtUtils.getHeader(), jwt);
        return ResultData.succMsg("登录成功!");
    }
}
