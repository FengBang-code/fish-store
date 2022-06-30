package com.yutang.service.action;

import com.yutang.config.ResultData;
import com.yutang.entity.Book;
import com.yutang.entity.Customer;
import com.yutang.entity.Order;
import com.yutang.mapper.OrderMapper;
import com.yutang.model.background.BookRecordVO;
import com.yutang.model.background.BookTopVO;
import com.yutang.model.foreground.PurchaseBooksDTO;
import com.yutang.service.BookService;
import com.yutang.service.OrderService;
import com.yutang.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author fzhuoxing
 * @Time 2022/4/18 10:33 上午
 * 订单service
 */

@Service
public class Order_Service {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @Resource
    OrderMapper orderMapper;

    @Autowired
    BookService bookService;

    // 订单号的生成,生成完之后要进行数据库的查询。
    public String orderNumCreated() throws Exception {
        // X20220422778
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        for(int i = 0; i < 5; i++) {
            // 订单号尾数的生成
            String tail = tailNum();
            // 订单号
            String orderNum = "X" + dtf.format(LocalDateTime.now()) + tail;
            Order order = orderMapper.findOrderByNum(orderNum);
            if(Objects.isNull(order)) {
                return orderNum;
            }
        }
        throw new Exception("订单号生成失败");
    }

    // 生成后3位的随机号
    public String tailNum() {
        String str = "";
        for(int i =0 ; i < 3; i++) {
            String tail = String.valueOf(0 + (int)(Math.random() * 10));
            str = str.concat(tail);
        }
        return str;
    }

    /**
     * 创建订单
     * @return
     */
    public ResultData createOrder(String orderState , Customer customer, PurchaseBooksDTO model) throws Exception {
        // 创建订单号
        String orderNum = orderNumCreated();
        Order order = new Order();
        order.setOrderRemark(model.getOrderRemark());
        order.setOrderNum(orderNum);
        order.setOrderState(orderState);
        if(model.getPayType().equals("0"))
            order.setOrderAmount(model.getOrderAmount().multiply(new BigDecimal("0.8")));
        else
            order.setOrderAmount(model.getOrderAmount());
        order.setCusName(customer.getCusName());
        if(orderState.equals("1")) {
            order.setDebt(model.getOrderAmount().subtract(model.getRealityAmount()));
        }
        String bookIds = "";
        // bookId-count
        for(int i = 0; i < model.getIds().size(); i++) {
            bookIds = bookIds + model.getIds().get(i) + "-" +model.getCount().get(i) + ",";
        }
        bookIds = bookIds.substring(0,bookIds.length() - 1);
        order.setBookIds(bookIds);
        order.setCusId(model.getCusId());
        // 插入订单表
        orderMapper.insertOrder(order);
        // 创建流水
        accountService.createAccount(customer, model, orderNum);
        return ResultData.succMsg(order);
    }

    /**
     * 返回售书记录
     */
    public BookRecordVO getRecord() {
        BookRecordVO vo = new BookRecordVO();
        List<Book> bookList = bookService.list();
        // id与书类
        Map<Long, String> bookType = new HashMap<>();
        // id与价格
        Map<Long, BigDecimal> bookValue = new HashMap<>();
        // 书本出现次数 id-数量
        Map<Long, Integer> bookCount = new HashMap<>();
        bookList.forEach(model -> {
            bookType.put(model.getId(),model.getCategory());
            bookValue.put(model.getId(),model.getPrice());
            bookCount.put(model.getId(), 0);
        });

        // 获取本周订单
        List<Order> orderList = getBookTop();
        List<Long> bookIds = new ArrayList<>();
        List<Integer> bookNums = new ArrayList<>();
        orderList.forEach(model -> {
            String[] split = model.getBookIds().split(",");
            for (String s : split) {
                String[] split1 = s.split("-");
                bookIds.add(Long.parseLong(split1[0]));
                bookNums.add(Integer.parseInt(split1[1]));
                // 当前Map中该书本的数量
//                int num = bookCount.get(Long.parseLong(split1[0]));
//                bookCount.put(Long.parseLong(split1[0]), num + Integer.parseInt(split1[1]));
            }
        });
        // 柱形图->找书类对映的数量
        Map<String, Integer> barMap = new HashMap<>();
        barMap.put("小说", 0);
        barMap.put("世界名著", 0);
        barMap.put("工具书", 0);
        barMap.put("教科书", 0);
        barMap.put("随笔", 0);
        // 找书类对映的金额
        Map<String, BigDecimal> priceMap = new HashMap<>();
        priceMap.put("小说", new BigDecimal("0.00"));
        priceMap.put("世界名著", new BigDecimal("0.00"));
        priceMap.put("工具书", new BigDecimal("0.00"));
        priceMap.put("教科书", new BigDecimal("0.00"));
        priceMap.put("随笔", new BigDecimal("0.00"));
        try {

//            for (Long bookId : bookIds) {
//                String type = bookType.get(bookId);
//                Integer num = barMap.get(type);
//                BigDecimal price = bookValue.get(bookId).multiply(new BigDecimal(num.toString()));
//                barMap.put(type, ++num);
//                priceMap.put(type, price);
//            }
            for(int i = 0; i < bookIds.size(); i++) {
                String type = bookType.get(bookIds.get(i));
                Integer num = bookNums.get(i);
                BigDecimal price = bookValue.get(bookIds.get(i)).multiply(new BigDecimal(num.toString()));
                barMap.put(type, barMap.get(bookType.get(bookIds.get(i))) + num);
                priceMap.put(type, price);
            }

//            bookIds.forEach(id -> {
//                String type = bookType.get(id);
//                Integer num = barMap.get(type);
//                BigDecimal price = bookValue.get(id).multiply(new BigDecimal(num.toString()));
//                barMap.put(type, ++num);
//                priceMap.put(type, price);
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Integer> barList = new ArrayList<>();
        barMap.forEach((k,v) -> {
            barList.add(v);
        });
        List<BigDecimal> price = new ArrayList<>();
        priceMap.forEach((k,v) -> {
            price.add(v);
        });
        vo.setBarList(barList);
        vo.setCirList(price);

        // 找出top5的书本【一周内】
        List<Order> orders = getBookTop();
        orders.forEach(model -> {
            String[] split = model.getBookIds().split(",");
            for (String s : split) {
                String[] split1 = s.split("-");
                // 当前Map中该书本的数量
                int num = bookCount.get(Long.parseLong(split1[0]));
                bookCount.put(Long.parseLong(split1[0]), num + Integer.parseInt(split1[1]));
            }
        });
        // HashMap排序
        ArrayList<Map.Entry<Long, Integer>> list = new ArrayList<>(bookCount.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Long, Integer>>() {
            @Override
            public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue()); //前面加负号，降序
            }
        });
        // 取前五本书id
        // 如果一周内都没有5种书产生就会报错，先不管了。
        List<BookTopVO> tops = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
//            ids.add(list.get(i).getKey());
            BookTopVO bookTopVO = new BookTopVO();
            bookTopVO.setBookName(bookService.getById(list.get(i).getKey()).getBookName());
            bookTopVO.setBookNum(getCount(list.get(i).getKey()));
            tops.add(bookTopVO);
        }
        vo.setBookTopVOList(tops);
        vo.setTimes(getTime());
        return vo;
    }

    /**
     * 获取本周内的订单
     * @return
     */
    public List<Order> getBookTop() {
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd 00:00:00");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        date = calendar.getTime();
        String begin = df.format(date);
        SimpleDateFormat df1 = new SimpleDateFormat("YYYY-MM-dd 23:59:59");
        String end = df1.format(new Date());
        return orderMapper.getOrdersByTime(begin, end);
    }

    /**
     * 一周时间的数组
     */
    public List<String> getTime() {
        List<String> times = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 6; i >= 0; i--) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -i);
            Date date1 = c.getTime();
            String time = sdf.format(date1);
            times.add(time);
        }
        return times;
    }

    /**
     * 获取该书在一周内每天的销量
     */
    public List<Integer> getCount(Long bookId) {
        List<Integer> count = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 6; i >= 0; i--) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -i);
            Date date1 = c.getTime();
            String begin = sdf.format(date1);
            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.DATE, -i+1);
            Date date2 = c1.getTime();
            String end = sdf.format(date2);
            List<Order> orders = orderMapper.getOrdersByTime(begin, end);
            count.add(getBookCount(bookId, orders));
        }

        return count;
    }

    /**
     * 通过书本id解析书本数量[当天]
     * @return
     */
    public Integer getBookCount(Long bookId, List<Order> orders) {
        int count = 0;
        for (Order order : orders) {
            String[] split = order.getBookIds().split(",");
            for (String s : split) {
                String[] split1 = s.split("-");
                if(split1[0].equals(String.valueOf(bookId))) {
                    count = count + Integer.valueOf(split1[1]);
                }
            }
        }
        return count;
    }

}
