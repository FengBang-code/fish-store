package com.yutang.config.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 4:02 下午
 */

@Mapper
public interface OrderStruct {
    OrderStruct INSTANCES = Mappers.getMapper(OrderStruct.class);
}
