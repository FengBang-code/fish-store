package com.yutang.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author fzhuoxing
 * @Time 2022/4/14 4:05 下午
 */

@Configuration
@MapperScan("com.yutang.mapper")
public class MyBatisPlusConfig implements MetaObjectHandler{
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 防止全表更新
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    // 注入了批量插入操作
    @Bean
    public EasySqlInjector easySqlInjector() {
        return new EasySqlInjector();
    }

    // 默认填充逻辑删除的字段
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("del_flag","0",metaObject);
    }

    // 在更新的时候，默认加上一些字段（类似时间）
    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
