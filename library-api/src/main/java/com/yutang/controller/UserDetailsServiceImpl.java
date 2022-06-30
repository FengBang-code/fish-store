//package com.yutang.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.yutang.entity.Customer;
//import com.yutang.entity.Employee;
//import com.yutang.service.CustomerService;
//import com.yutang.service.EmployeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Objects;
//
///**
// * @Author fzhuoxing
// * @Time 2022/4/14 8:56 下午
// * security进行密码验证
// */
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    EmployeeService employeeService;
//    @Autowired
//    BCryptPasswordEncoder encoder;
//    @Autowired
//    CustomerService customerService;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 获取MAN_，CUS_
//        String preStr = username.substring(0,4);
//        if (preStr.equals("MAN_")) {
//            QueryWrapper wrapper = (QueryWrapper) new QueryWrapper().eq("emp_num",username);
//            Employee emp = employeeService.getOne(wrapper);
//            if (Objects.isNull(emp)) {
//                throw new UsernameNotFoundException("用户名或者密码错误");
//            }
//            Collection<GrantedAuthority> authorities = new ArrayList<>();
//            User user = new User(username, encoder.encode(emp.getEmpPass()), authorities);
//            return user;
//        } else if (preStr.equals("CUS_")) {
//            QueryWrapper wrapper = (QueryWrapper) new QueryWrapper().eq("cus_num",username);
//            Customer cus = customerService.getOne(wrapper);
//            if (Objects.isNull(cus)) {
//                throw new UsernameNotFoundException("用户名或者密码错误");
//            }
//            Collection<GrantedAuthority> authorities = new ArrayList<>();
//            User user = new User(username, encoder.encode(cus.getCusPass()), authorities);
//            return user;
//        }
//        return null;
//    }
//}
