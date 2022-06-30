package com.yutang.service.impl;

import com.yutang.entity.Employee;
import com.yutang.mapper.EmployeeMapper;
import com.yutang.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fzhuoxing
 * @since 2022-04-14
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
