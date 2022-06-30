//package com.yutang.config;
//
//import com.yutang.controller.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
///**
// * @Author fzhuoxing
// * @Time 2022/4/14 2:58 下午
// */
//
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    UserDetailsServiceImpl userDetailsService;
//    //加密形式
//    @Bean
//    BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//    }
//    // 白名单
//    private static final String[] URL_WHITELIST = {
//            "/login",
//            "/logout",
//            "/captcha",
//            "/favicon.ico",
//            "/demo"
//    };
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable()
//                .authorizeRequests()
//                .antMatchers(URL_WHITELIST).permitAll()
//                .anyRequest().authenticated();
//    }
//
//}
