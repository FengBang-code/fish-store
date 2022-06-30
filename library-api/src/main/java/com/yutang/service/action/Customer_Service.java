package com.yutang.service.action;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yutang.config.ResultData;
import com.yutang.config.mapstruct.CustomerStruct;
import com.yutang.entity.Book;
import com.yutang.entity.Customer;
import com.yutang.entity.Order;
import com.yutang.mapper.BookMapper;
import com.yutang.mapper.CustomerMapper;
import com.yutang.model.background.BookUpdateDTO;
import com.yutang.model.foreground.*;
import com.yutang.service.BookService;
import com.yutang.service.CustomerService;
import com.yutang.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 2:51 下午
 * 与用户相关的逻辑
 */

@Service
public class Customer_Service {
    @Autowired
    CustomerService customerService;
    @Autowired
    BookService bookService;
    @Resource
    CustomerMapper customerMapper;
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    Order_Service orderService;
    @Autowired
    AccountService accountService;
    @Resource
    BookMapper bookMapper;
    /**
     * 新增用户
     * @param model
     * @return
     */
    public ResultData addCustomer(CustomerDTO model) {
        if(duplicate(model.getCusNum())) {
            return ResultData.failMsg("该用户名已存在请重新输入！");
        }
        Customer customer = CustomerStruct.INSTANCES.toCustomer(model);
        customerService.save(customer);
        return ResultData.succMsg("操作成功");
    }

    /**
     * 查看是否存在重复的用户名
     * 重名 -> true, 不重名 -> false
     * @param cusNum
     * @return
     */
    public Boolean duplicate(String cusNum) {
        QueryWrapper wrapper = (QueryWrapper) new QueryWrapper().eq("cus_num", cusNum);
        Customer cus = customerService.getOne(wrapper);
        if(Objects.isNull(cus)) {
            return false;
        }
        return true;
    }

    /**
     * 用户查询图书
     * @param model
     * @return
     */
    public ResultData getBooks(CustomerBookDTO model) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery(Book.class)
                // 模糊搜索书名
                .like(!Objects.isNull(model.getBookName()) && !model.getBookName().equals(""),Book::getBookName,model.getBookName())
                // 精准查询isbn
                .eq(!Objects.isNull(model.getIsbn()) && !model.getIsbn().equals(""),Book::getIsbn,model.getIsbn())
                // 模糊查询作者
                .like(!Objects.isNull(model.getAuthor()) && !model.getAuthor().equals(""),Book::getAuthor,model.getAuthor())
                // 模糊查询出版社
                .like(!Objects.isNull(model.getPublication()) && !model.getPublication().equals(""),Book::getPublication,model.getPublication())
                // 只有启用的书才能被用户看到
                .eq(Book::getEnabled, true)
                // 书类查询
                .eq(!Objects.isNull(model.getCategory()) && !model.getCategory().equals(""),Book::getCategory, model.getCategory());
        IPage<Book> page = new Page<>();
        page.setCurrent(model.getCurrent());
        page.setSize(model.getPageSize());
        IPage<Book> page1 = bookService.page(page, wrapper);
        List<CustomerBookVO> bookList = CustomerStruct.INSTANCES.toCusBookVo(page1.getRecords());
        return ResultData.succMsg(bookList);
    }

    /**
     * 用户修改密码
     * @param model
     * @return
     */
    public ResultData revisePassword(HttpServletRequest req, RevisePassDTO model) {
        // 获取当前登录的用户的账号
        ResultData result = personal(req);
        Customer cus = (Customer)result.getData();
        if (!model.getOriginalPass().equals(cus.getCusPass())) {
            return ResultData.failMsg("原密码错误，请重新输入～");
        }
        cus.setCusPass(model.getPassword());
        customerService.updateById(cus);
        return ResultData.succMsg("修改密码成功！");
    }

    /**
     * 返回个人中心字段
     * @param req
     * @return
     */
    public ResultData personal(HttpServletRequest req) {
        // 通过jwt来获取用户名
        String token = req.getHeader("Authorization");
        String subject = jwtUtils.getClaimByToken(token).getSubject();
        // 提取token中的账号 customer_lisi --> lisi
        String[] s = subject.split("_");
        String customerNum = s[1];
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("cus_num", customerNum);
        // 获取用户信息
        Customer cus = customerService.getOne(wrapper);
        // 返回用户信息
        return ResultData.succMsg(cus);
    }

    /**
     * 购书（假设正常下单不会失败）
     * 有一本书库存不足，或者本站支付金额不足下单就会失败。
     * 非本站即使不够钱也可以下单创建订单和流水
     *
     * @param model
     * @return
     */
    public ResultData purchaseBooks(HttpServletRequest req, PurchaseBooksDTO model) throws Exception {
        // 通过token获取用户名（账号）
        String header = req.getHeader("Authorization");
        String subject = jwtUtils.getClaimByToken(header).getSubject();
        String[] s = subject.split("_");
        String cusNum = s[1];
        // 通过客户账号查询该用户的本站余额是否足够
        QueryWrapper<Customer> wrapper = new QueryWrapper();
        wrapper.eq("cus_num",cusNum);
        Customer cus = customerService.getOne(wrapper);
        BigDecimal cusBalance = cus.getBalance();
        // 判断本站支付是否存在余额不足
//        Boolean cusBalanceFlag = model.getRealityAmount().multiply(new BigDecimal("0.8")).compareTo(cusBalance) > 0;
        Boolean cusBalanceFlag = model.getRealityAmount().compareTo(cusBalance) > 0;
        // 判断非本站支付是否存在欠款
        Boolean isDue = model.getRealityAmount().compareTo(model.getOrderAmount()) < 0;
        // 判断支付类型,0 -> 本站支付
        if(model.getPayType().equals("0")) {
            if(cusBalanceFlag) {
                return ResultData.failMsg("您的账号本站余额不足，下单失败！");
            }
            bookCheckAndUpdate(model);
        }
        // 1 -> 网银支付
        else if(model.getPayType().equals("1")) {
            bookCheckAndUpdate(model);
        }
        // 2 -> 现金支付
        else if(model.getPayType().equals("2")) {
            bookCheckAndUpdate(model);
        }
        // 写入订单表
        QueryWrapper<Customer> cusWrapper = new QueryWrapper<>();
        cusWrapper.eq("cus_num", cusNum);
        Customer customer = customerService.getOne(cusWrapper);


        // 支付类型（0为本站支付，1为网银支付，2为现金支付,3为退款）
        // 订单状态(0为完成支付，1为欠款，2退款完成)
        // 创建订单以及流水
        if(model.getPayType().equals("0") && !cusBalanceFlag) {
            orderService.createOrder("0",customer,model);
            // 修改用户的本站金额
            customerMapper.updateBalance(cusBalance.subtract(model.getRealityAmount().multiply(new BigDecimal("0.8"))), cus.getId());
        }
        else if(!model.getPayType().equals("0")) {
            if(isDue) {
                orderService.createOrder("1",customer,model);
            }
            else {
                orderService.createOrder("0",customer,model);
            }
        }
        return ResultData.succMsg("下单成功！");
    }

    public ResultData bookCheckAndUpdate(PurchaseBooksDTO model) throws Exception {
        // 查询库存,防止并发问题
        for(int i = 0; i < model.getIds().size(); i++) {
            Book book = bookService.getById(model.getIds().get(i));
            // 需求大于库存的时候就返回失败
            if(book.getStock() < model.getCount().get(i)) {
                throw new Exception(book.getBookName() + "数量不足请重新下单!");
            }
        }
        // 更新库存
        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO();
        for(int i = 0; i < model.getIds().size(); i++) {
            bookUpdateDTO.setId(model.getIds().get(i));
            Book book = bookService.getById(model.getIds().get(i));
            bookUpdateDTO.setStock(book.getStock() - model.getCount().get(i));
            bookMapper.updateBook(bookUpdateDTO);
        }
        return ResultData.succMsg("");
    }
}
