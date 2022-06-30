package com.yutang.mapper;

import com.yutang.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yutang.model.background.CustomerAddDTO;
import com.yutang.model.background.CustomerAddRes;
import com.yutang.model.background.CustomerUpdateDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fzhuoxing
 * @since 2022-04-14
 */
public interface CustomerMapper extends BaseMapper<Customer> {
    @Update("update customer set last_login = #{time} where cus_num = #{cusNum}")
    Integer updateLastLogin(String time, String cusNum);

    void updateCustomer(@Param("model")CustomerUpdateDTO model);

//    @Update("update customer set cus_pass = #{pass}")
//    void revisePassword(String pass);

    @Select("select cus_name from customer where cus_num = #{num}")
    String findNameByNum(String num);

    // 更新该用户的本站余额
    @Update("update customer set balance = #{balance} where id = #{id}")
    Boolean updateBalance(BigDecimal balance, Long id);

    @Select("select * from customer where id = #{id}")
    Customer findById(Long id);

    // 插入用户
    @Insert("insert into customer (cus_name,cus_num,cus_pass,phone,identity,address) values (#{model.cusName},#{model.cusNum},#{model.cusPass},#{model.phone},#{model.identity},#{model.address})")
    Integer addCustomer(@Param("model") CustomerAddRes model);

    // 查重（账号）
    @Select("select cus_num from customer where cus_num = #{cusNum}")
    String existCustomer(String cusNum);
}
