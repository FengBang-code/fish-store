package com.yutang.service.action;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yutang.config.ResultData;
import com.yutang.config.mapstruct.CustomerStruct;
import com.yutang.config.mapstruct.EmployeeStruct;
import com.yutang.entity.Book;
import com.yutang.entity.Customer;
import com.yutang.entity.Employee;
import com.yutang.mapper.BookMapper;
import com.yutang.mapper.CustomerMapper;
import com.yutang.model.background.*;

import com.yutang.service.BookService;
import com.yutang.service.CustomerService;
import com.yutang.service.EmployeeService;
import org.mapstruct.ap.internal.model.assignment.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 3:32 下午
 */

@Service
public class Employee_Service {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    BookService bookService;
    @Autowired
    CustomerService customerService;
    @Resource
    BookMapper bookMapper;
    @Resource
    CustomerMapper customerMapper;

    //***********************员工***********************//
    /**
     * 添加员工
     * @param model
     * @return
     */
    public ResultData addEmployee(EmployeeDTO model) {
        if(duplicate(model.getEmpNum())) {
            return ResultData.failMsg("工号已存在,请重新输入!");
        }
        Employee emp = EmployeeStruct.INSTANCES.toEmployee(model);
        employeeService.save(emp);
        return ResultData.succMsg("操作成功");
    }

    /**
     * 检测是否存在相同的工号
     * @param empNum
     * @return
     */
    public Boolean duplicate(String empNum) {
        QueryWrapper<Employee> wrapper = (QueryWrapper) new QueryWrapper().eq("emp_num", empNum);
        Employee cus = employeeService.getOne(wrapper);
        if(Objects.isNull(cus)) {
            return false;
        }
        return true;
    }
    //***********************图书***********************//
    /**
     * 查询图书列表
     * @param model
     * @return
     */
    public ResultData getBooks(EmployeeBookDTO model) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery(Book.class)
                // 模糊搜索书名
                .like(!Objects.isNull(model.getBookName()) && !model.getBookName().equals(""),Book::getBookName,model.getBookName())
                // 精准查询isbn
                .eq(!Objects.isNull(model.getIsbn()) && !model.getIsbn().equals(""),Book::getIsbn,model.getIsbn())
                // 是否启用
                .eq(!Objects.isNull(model.getEnabled()),Book::getEnabled,model.getEnabled())
                // 模糊查询作者
                .like(!Objects.isNull(model.getAuthor()) && !model.getAuthor().equals(""),Book::getAuthor,model.getAuthor())
                // 模糊查询出版社
                .like(!Objects.isNull(model.getPublication()) && !model.getPublication().equals(""),Book::getPublication,model.getPublication())
                // 书类
                .eq(!Objects.isNull(model.getCategory()) && !model.getCategory().equals(""),Book::getCategory, model.getCategory());
        IPage<Book> page = new Page<>();
        page.setCurrent(model.getCurrent());
        page.setSize(model.getPageSize());
        IPage<Book> page1 = bookService.page(page, wrapper);
        return ResultData.succMsg(page1.getRecords());
    }

    /**
     * 设置是否启用
     * @param model
     * @return
     */
    public ResultData isEnabled(BookEnabledDTO model) {
        // 启用 -> 设置启用时间
        if(model.getEnabled()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDate = dtf.format(LocalDateTime.now());
            bookMapper.enabledAction(formatDate,model.getId());
        } else {
            // 不启用直接更新 -> 里面包含不启用原因
            Book book = EmployeeStruct.INSTANCES.toBookFromEnabled(model);
            bookService.updateById(book);
        }
        return ResultData.succMsg("操作成功");
    }

    /**
     * 更新书本，也就是更新库存与价格
     * @param model
     * @return
     */
    public ResultData bookUpdate(BookUpdateDTO model) {
        bookMapper.updateBook(model);
        return ResultData.succMsg("操作成功");
    }

    /**
     * 通过id获取书本信息
     * @param model
     * @return
     */
    public ResultData bookInfo(BaseId model) {
        return ResultData.succMsg(bookService.getById(model.getId()));
    }

    /**
     * 逻辑删除书本
     * @param model
     * @return
     */
    public ResultData bookRemove(BaseId model) {
        bookService.removeById(model.getId());
        return ResultData.succMsg("删除成功");
    }

    /**
     * 新增图书
     * @param model
     * @return
     */
    public ResultData bookAdd(BookAddDTO model) {
        // 检测重复书本
        if(!duplicateOfBook(model.getIsbn()))
            return ResultData.failMsg("图书ISBN = " + model.getIsbn() + "已存在！");
        bookService.save(EmployeeStruct.INSTANCES.toBookFromAdd(model));
        return ResultData.succMsg("新增图书成功！");
    }

    /**
     * 检测是否存在重复的书本
     * @param ISBN
     * @return
     */
    public Boolean duplicateOfBook(String ISBN) {
        QueryWrapper<Book> wrapper = new QueryWrapper();
        wrapper.eq("isbn", ISBN);
        Book book = bookService.getOne(wrapper);
        if(Objects.isNull(book)) {
            return true;
        }
        return false;
    }
    //***********************客户***********************//

    /**
     * 查询客户列表
     * @param model
     * @return
     */
    public ResultData getCustomers(CustomerListDTO model) {
        LambdaQueryWrapper<Customer> wrapper = Wrappers.lambdaQuery(Customer.class)
                // 模糊搜索客户名
                .like(!Objects.isNull(model.getCusName()) && !model.getCusName().equals(""),Customer::getCusName,model.getCusName())
                // 模糊查询账号
                .like(!Objects.isNull(model.getCusNum()) && !model.getCusNum().equals(""),Customer::getCusNum,model.getCusNum())
                // 模糊查询手机号
                .like(!Objects.isNull(model.getPhone()) && !model.getPhone().equals(""),Customer::getPhone,model.getPhone());
        IPage<Customer> page = new Page<>();
        page.setCurrent(model.getCurrent());
        page.setSize(model.getPageSize());
        IPage<Customer> page1 = customerService.page(page, wrapper);
        return ResultData.succMsg(page1.getRecords());
    }

    /**
     * 通过id查询用户的详情
     * @param model
     * @return
     */
    public ResultData customersInfo(BaseId model) {
        return ResultData.succMsg(customerService.getById(model.getId()));
    }

    /**
     * 更新用户信息（手机号和常用地址）
     * @param model
     * @return
     */
    public ResultData updateCustomer(CustomerUpdateDTO model) {
        customerMapper.updateCustomer(model);
        return ResultData.succMsg("操作成功！");
    }

    /**
     * 逻辑删除
     * @param model
     * @return
     */
    public ResultData removeCustomer(BaseId model) {
        // 只有本站余额为0的时候才可以删除用户
        BigDecimal balance = customerService.getById(model.getId()).getBalance();
        if(balance.compareTo(new BigDecimal("0.00")) != 0) {
            return ResultData.failMsg("操作失败,该用户的余额不为0");
        }
        customerService.removeById(model.getId());
        return ResultData.succMsg("操作成功！");
    }

    /**
     * 添加新用户，默认密码是身份证后6位
     * @param model
     * @return
     */
    public ResultData addCustomer(CustomerAddDTO model) {
        CustomerAddRes cusRes = EmployeeStruct.INSTANCES.toCustomerRes(model);
        // 查看是否存在相同的账号
        String s = customerMapper.existCustomer(model.getCusNum());
        if (!Objects.isNull(s)) {
            return ResultData.failMsg("用户名 " + s + ",已存在！");
        }
        // 默认密码是身份证后6位
        cusRes.setCusPass(model.getIdentity().substring(12,18));
        customerMapper.addCustomer(cusRes);
        return ResultData.succMsg(cusRes);
    }

    /**
     * 可以批量初始化密码
     * @param model
     * @return
     */
    public ResultData updateCustomerPass(BaseIds model) {
        model.getIds().forEach(id -> {
            Customer cus = customerService.getById(id);
            cus.setCusPass(cus.getIdentity().substring(12,18));
            customerService.saveOrUpdate(cus);
        });
        return ResultData.succMsg("操作成功！");
    }

}
