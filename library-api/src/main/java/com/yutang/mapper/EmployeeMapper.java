package com.yutang.mapper;

import com.yutang.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fzhuoxing
 * @since 2022-04-14
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    @Update("update employee set last_login = #{time} where emp_num = #{empNum}")
    Integer updateLastLogin(String time, String empNum);
}
