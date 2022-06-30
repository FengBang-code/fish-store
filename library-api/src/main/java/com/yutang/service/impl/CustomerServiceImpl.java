package com.yutang.service.impl;

import com.yutang.entity.Customer;
import com.yutang.mapper.CustomerMapper;
import com.yutang.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fzhuoxing
 * @since 2022-04-14
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
