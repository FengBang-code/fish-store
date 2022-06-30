package com.yutang.config.mapstruct;

import com.yutang.entity.Book;
import com.yutang.entity.Customer;
import com.yutang.model.RegisterDTO;
import com.yutang.model.foreground.CustomerBookVO;
import com.yutang.model.foreground.CustomerDTO;
import com.yutang.model.foreground.CustomerPersonalVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 3:07 下午
 */

@Mapper
public interface CustomerStruct {
    CustomerStruct INSTANCES = Mappers.getMapper(CustomerStruct.class);
    Customer toCustomer(CustomerDTO dto);
    List<CustomerBookVO> toCusBookVo(List<Book> list);
    Customer toCustomerFromRegister(RegisterDTO dto);
    CustomerPersonalVO toCustomerVo(Customer model);
}
