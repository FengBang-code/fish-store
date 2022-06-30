package com.yutang.service.action;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yutang.config.ResultData;
import com.yutang.entity.AccountStatement;
import com.yutang.entity.Customer;
import com.yutang.entity.Order;
import com.yutang.mapper.AccountStatementMapper;
import com.yutang.mapper.BookMapper;
import com.yutang.mapper.CustomerMapper;
import com.yutang.mapper.OrderMapper;
import com.yutang.model.background.*;
import com.yutang.model.foreground.PurchaseBooksDTO;
import com.yutang.service.AccountStatementService;
import com.yutang.service.CustomerService;
import com.yutang.service.OrderService;
import com.yutang.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author fzhuoxing
 * @Time 2022/4/18 10:33 上午
 * 流水service
 */

@Service
public class AccountService {
    @Autowired
    CustomerService customerService;
    @Autowired
    AccountStatementService accountStatementService;
    @Resource
    AccountStatementMapper accountStatementMapper;
    @Autowired
    Order_Service orderService;
    @Resource
    OrderMapper orderMapper;
    @Resource
    CustomerMapper customerMapper;
    @Resource
    BookMapper bookMapper;
    @Autowired
    JWTUtils jwtUtils;

    // 写一段连表查询的sql
    public ResultData queryAccount(AccountQueryDTO model) {
        // 传递分页中的offset参数
        long offsetNum = model.getPageSize() * (model.getCurrent() - 1);
        List<AccountQueryVO> list = accountStatementMapper.queryAccount(model, offsetNum);
        for (AccountQueryVO accountQueryVO : list) {
            if(accountQueryVO.getPayType().equals("0")) {
                accountQueryVO.setPayType("本站支付");
            }
            else if(accountQueryVO.getPayType().equals("1")) {
                accountQueryVO.setPayType("网银支付");
            }
            else if(accountQueryVO.getPayType().equals("2")) {
                accountQueryVO.setPayType("现金支付");
            }
            else if(accountQueryVO.getPayType().equals("3")) {
                accountQueryVO.setPayType("退款");
            }
        }
        return ResultData.succMsg(list);
    }

    // 流水号的生成
    public String accountNum(String orderNum) throws Exception {
        for(int i = 0; i < 5; i++) {
            String tail = accountTail();
            String accountNum = orderNum + tail;
            QueryWrapper<AccountStatement> wrapper = new QueryWrapper<>();
            wrapper.eq("account_num", accountNum);
            if(Objects.isNull(accountStatementService.getOne(wrapper))) {
                return accountNum;
            }
        }
        throw new Exception("流水号生成失败");
    }

    // 生成流水号后两位随机数
    public String accountTail() {
        String str = "";
        for(int i =0 ; i < 2; i++) {
            String tail = String.valueOf(0 + (int)(Math.random() * 10));
            str = str.concat(tail);
        }
        return str;
    }

    /**
     * 创建流水(购书)
     * @return
     */
    public ResultData createAccount(Customer customer, PurchaseBooksDTO model, String orderNum) throws Exception {
        // 创建流水号
        String accountNum = accountNum(orderNum);
        // 插入流水表
        AccountStatement accountStatement = new AccountStatement();
        accountStatement.setAccountNum(accountNum);
        accountStatement.setCusName(customer.getCusName());
        accountStatement.setCusNum(customer.getCusNum());
        accountStatement.setOrderNum(orderNum);
        if(model.getPayType().equals("0")){
            accountStatement.setAccountAmount(model.getOrderAmount().multiply(new BigDecimal("0.8")));
            accountStatement.setAccountRemark("本站支付0.8折");
        }
        else
            accountStatement.setAccountAmount(model.getOrderAmount());
        accountStatement.setPayType(model.getPayType());
        accountStatementService.save(accountStatement);
        return ResultData.succMsg(accountStatement);
    }

    /**
     * 修改流水信息
     * @param model
     * @return
     */
    public ResultData updateAccount(AccountUpdateDTO model) {
        accountStatementMapper.updateAccount(model);
        return ResultData.succMsg("操作成功");
    }

    /**
     * 通过id获取流水详细
     * @param model
     * @return
     */
    public ResultData AccountInfo(BaseId model) {
        // 返回AccountStatement
        AccountInfoVO vo = new AccountInfoVO();
        AccountStatement account = accountStatementService.getById(model.getId());
        Order order = orderMapper.findOrderByNum(account.getOrderNum());
        // 设置订单相关的字段
        vo.setOrderRemark(order.getOrderRemark());
        vo.setOrderState(order.getOrderState());
        vo.setOrderNum(order.getOrderNum());
        vo.setCreateTime(order.getCreateTime());
        vo.setCusName(order.getCusName());
        List<String> bookNames = new ArrayList<>();
        List<Integer> bookCount = new ArrayList<>();
        // false存bookNames, true存bookCount
        boolean changeFlag = false;
        // 存储bookNames,bookCount
        for(String s : order.getBookIds().split(",")) {
            for(String s1 : s.split("-")) {
                if(!changeFlag) {
                    changeFlag = true;
                    bookNames.add(bookMapper.findBookNameById(Long.valueOf(s1)));
                }
                else {
                    bookCount.add(Integer.valueOf(s1));
                    changeFlag = false;
                }
            }
        }
        vo.setBookCount(bookCount);
        vo.setBookNames(bookNames);
        // 设置流水相关的字段
        vo.setAccountAmount(account.getAccountAmount());
        vo.setAccountRemark(account.getAccountRemark());
        vo.setAccountNum(account.getAccountNum());
        vo.setPayType(account.getPayType());
        // 设置手机号
        String phone = customerMapper.findById(order.getCusId()).getPhone();
        vo.setPhone(phone);
        return ResultData.succMsg(vo);
    }

    /**
     * 退款
     * @return
     */
    public ResultData refund(HttpServletRequest req, RefundDTO model) throws Exception {
        // 通过jwt来获取用户名
        String token = req.getHeader("Authorization");
        String subject = jwtUtils.getClaimByToken(token).getSubject();
        // 提取token中的账号 customer_lisi --> lisi
        String[] s = subject.split("_");
        String customerNum = s[1];
        model.setCusNum(customerNum);
        // 要是本站支付的话就往用户中打钱
        if(model.getPayType().equals("3")) {
            Customer cus = customerService.getOne(new QueryWrapper<Customer>().eq("cus_name", model.getCusName()));
            cus.setBalance(cus.getBalance().add(model.getAccountAmount()));
            customerService.updateById(cus);
        }
        // 创建一条流水
        AccountStatement statement = new AccountStatement();
        statement.setOrderNum(model.getOrderNum());
        statement.setAccountNum(accountNum(model.getOrderNum()));
        statement.setCusNum(customerNum);
        statement.setAccountRemark(model.getAccountRemark());
        statement.setPayType("3");
        statement.setCusName(model.getCusName());
        statement.setAccountAmount(model.getAccountAmount());
        accountStatementService.save(statement);
        return ResultData.succMsg("退款成功");
    }

}
