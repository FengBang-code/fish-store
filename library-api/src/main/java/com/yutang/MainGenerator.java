package com.yutang;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @Author fzhuoxing
 * @Time 2022/4/14 2:24 下午
 */

public class MainGenerator {
    static String packageName = "com.yutang"; // 当前包名
    static String author = "fzhuoxing"; // 作者
    static String sqlUrl = "mysql://fzhuoxing.work:3306/"; // 数据库类型及地址
    static String sqlDb = "sys_library"; // 数据库名
    static String sqlUser = "root";
    static String sqlPassword = "123456";
    static String table = "account_statement,book,order,employee,customer"; // 表，用逗号隔开

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //数据库
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:" + sqlUrl + sqlDb + "?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(sqlUser);
        dsc.setPassword(sqlPassword);
        mpg.setDataSource(dsc);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(System.getProperty("user.dir") +"/src/main/java"); //user.dir找到工程的绝对路径
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setFileOverride(true); //是否覆盖
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        mpg.setGlobalConfig(gc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName);
//        pc.setModuleName("generator");//把controller,entity等先放到generator中
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setXml("xml");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);//实体类自动加Lombok注解
        strategy.setNaming(NamingStrategy.underline_to_camel); // 数据库表名映射到类名的策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//实体类下划线转驼峰
        strategy.setRestControllerStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setInclude(table.split(","));
//        strategy.setTablePrefix()
        mpg.setStrategy(strategy);

        mpg.execute();//执行
    }
}
