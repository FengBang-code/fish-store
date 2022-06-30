package com.yutang.service.impl;

import com.yutang.entity.Order;
import com.yutang.mapper.OrderMapper;
import com.yutang.service.OrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
