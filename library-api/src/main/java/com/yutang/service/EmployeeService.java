package com.yutang.service;

import com.yutang.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fzhuoxing
 * @since 2022-04-14
 */
public interface EmployeeService extends IService<Employee> {
}
