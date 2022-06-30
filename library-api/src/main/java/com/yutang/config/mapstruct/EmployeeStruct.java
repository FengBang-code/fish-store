package com.yutang.config.mapstruct;

import com.yutang.entity.Book;
import com.yutang.entity.Customer;
import com.yutang.entity.Employee;
import com.yutang.model.background.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 3:36 下午
 * 管理员相关的类型转换
 */

@Mapper
public interface EmployeeStruct {
    EmployeeStruct INSTANCES = Mappers.getMapper(EmployeeStruct.class);
    Employee toEmployee(EmployeeDTO dto);
    Book toBookFromEnabled(BookEnabledDTO dto);
    Book toBookFromAdd(BookAddDTO dto);
    CustomerAddRes toCustomerRes(CustomerAddDTO dto);
    Customer toCustomerFromRes(CustomerAddRes res);
}
