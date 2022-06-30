package com.yutang.mapper;

import com.yutang.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fzhuoxing
 * @since 2022-04-14
 */
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select * from `order` where order_num = #{orderNum} and del_flag = '0'")
    Order findOrderByNum(String orderNum);

    @Insert("INSERT INTO `order` ( cus_name,order_num, order_remark, book_ids, order_amount, order_state,cus_id ) VALUES ( #{order.cusName},#{order.orderNum},#{order.orderRemark},#{order.bookIds},#{order.orderAmount},#{order.orderState},#{order.cusId} )")
    Integer insertOrder(@Param("order") Order order);

    @Select("select * from `order` where del_flag = '0'")
    List<Order> getOrders();

    @Select("select * from `order` where create_time between #{begin} and #{end}")
    List<Order> getOrdersByTime(@Param("begin")String begin, @Param("end")String end);

}
