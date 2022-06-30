package com.yutang.libraryapi;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yutang.entity.Book;
import com.yutang.model.background.BookTopVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 4:09 下午
 */

@SpringBootTest
public class NormalTest {
    @Test
    void test1() {
        System.out.println(Objects.isNull(""));
    }

    @Test
    void test2() {
        // 测试生成随机数
        for(int i = 0;i < 100; i++) {
            int num = 0 + (int)(Math.random() * (1000-0+1));

            System.out.println(num);
        }
    }

    // 订单号的生成
    @Test
    void test3() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        String tail = String.valueOf(0 + (int)(Math.random() * (1000-0+1)));
        String orderNum = "X" + dtf.format(LocalDateTime.now()) + tail;
        System.out.println(orderNum);
    }

    // 流水号生成
    @Test
    void test4() {
//        String tail = String.valueOf(0 + (int)(Math.random() * (10-0+1)));
//        String accountNum = "X20220421708" + tail;
//        System.out.println(accountNum);
//        System.out.println(tail);
        String str = "";
        for(int i =0 ; i < 3; i++) {
            String tail = String.valueOf(0 + (int)(Math.random() * (10-0+1)));
            str = str.concat(tail);
        }
        System.out.println(str);
    }
    @Test
    void test5() {
        String str = "abcde,2,3,f";
        System.out.println(str.substring(0, str.length() - 2));
    }

    @Test
    void test6() {
        // 测试bigDecimal
        System.out.println(new BigDecimal(0.8).multiply(new BigDecimal("396")));
    }

    @Test
    void test7() {
        String a = "11-2,4-5,13-9,44-9";
        String[] split = a.split(",");
        for (String s : split) {
            for (String s1 : s.split("-")) {
                System.out.println(s1);
            }
        }
    }
    @Test
    void test8() {
        String a = "你好";
        System.out.println(Integer.valueOf(a));
    }

    @Test
    void test9() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        map.put("d",4);
        map.put("a",5);
        map.put("c",6);
        map.forEach((k,v) -> {
            System.out.println(v);
        });
    }

    @Test
    void Test10() {
        Calendar calendar = Calendar.getInstance();
        int now = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(now == 0)
            now = 7;
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        calendar.add(Calendar.DATE, -now + 1);
        String begin = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DATE, now);
        String end = dateFormat.format(calendar.getTime());
        System.out.println(begin);
        System.out.println(end);
    }

    @Test
    void test11() {
        List<BookTopVO> vo = new ArrayList<>();

        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd 00:00:00");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        date = calendar.getTime();
        String begin = df.format(date);
        SimpleDateFormat df1 = new SimpleDateFormat("YYYY-MM-dd 23:59:59");
        String end = df1.format(new Date());
        System.out.println(begin);
        System.out.println(end);
    }

    @Test
    void test12() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 6; i >= 0; i--) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -i);
            Date date1 = c.getTime();
            String begin = sdf.format(date1);
        System.out.println(begin);
            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.DATE, -i+1);
            Date date2 = c1.getTime();
            String end = sdf.format(date2);
        System.out.println(end);
        }
    }
    @Test
    void test13() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 6; i >= 0; i--) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -i);
            Date date1 = c.getTime();
            String time = sdf.format(date1);
            System.out.println(time);
        }
    }

    @Test
    void test14() {

    }

}
