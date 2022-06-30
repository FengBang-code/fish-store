package com.yutang.model.foreground;


import lombok.Data;
import java.math.BigDecimal;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 2:40 下午
 * 用户获取书本的VO
 */

@Data
public class CustomerBookVO  {
    // id
    private Long id;
    // isbn
    private String isbn;
    // 图片url
    private String picUrl;
    // 书名
    private String bookName;
    // 作者
    private String author;
    // 出版社
    private String publication;
    // 书类
    private String category;
    // 是否启用
    private Boolean enabled;
    // 价格
    private BigDecimal price;
    // 库存
    private Integer stock;
    // 简介
    private String brief;
}
