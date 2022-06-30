package com.yutang.libraryapi;

import com.yutang.mapper.CustomerMapper;
import com.yutang.mapper.EmployeeMapper;
import com.yutang.model.LoginDTO;
import com.yutang.service.BookService;
import com.yutang.service.action.LoginService;
import com.yutang.service.action.Order_Service;
import com.yutang.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@SpringBootTest
class LibraryApiApplicationTests {

//    @Autowired
//    BCryptPasswordEncoder encoder;

    // 生成密码
//    @Test
//    void contextLoads() {
//        System.out.println(encoder.encode("a"));
//    }

    @Autowired
    LoginService loginService;
    // 测试登录接口
    @Test
    void checkLogin() {
        LoginDTO dto = new LoginDTO();
        dto.setUserNum("feng");
        dto.setPassword("a");
        dto.setState(1);
//        System.out.println(loginService.checkUser(dto));
    }

    @Test
    void checkDateTime() {
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time);
        // 自定义
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dtf.format(LocalDateTime.now()));
    }

    @Autowired
    EmployeeMapper employeeMapper;
    @Test
    void checkMapper() {
//        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(employeeMapper.updateLastLogin(dtf.format(LocalDateTime.now()), "a"));
    }

    @Autowired
    JWTUtils jwtUtils;
    @Test
    void CheckToken() {
        Claims claimByToken = jwtUtils.getClaimByToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjdXN0b21lcl9saXNpIiwiaWF0IjoxNjUxNTY4NDc5LCJleHAiOjE2NTIxNzMyNzl9.LDqFW8KZ9NETF5czQvh5w5PxZsjc7TfG3BNBjk4la-gwszogWRF4f4j1gjC5_9LyJrT9iuXMMe30co_vl5gN1Q");
        System.out.println(claimByToken.getSubject());
    }

    @Autowired
    CustomerMapper customerMapper;
    @Test
    void test1() {
        // 如果没有就返回null
        System.out.println(customerMapper.existCustomer("lisi"));
        System.out.println(Objects.isNull(customerMapper.existCustomer("abc")));
    }

    @Autowired
    BookService bookService;
    @Autowired
    Order_Service orderService;
    @Test
    void test3() {
        System.out.println(orderService.getCount(2L));
    }
}
