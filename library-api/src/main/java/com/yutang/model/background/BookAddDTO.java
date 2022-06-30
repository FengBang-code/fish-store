package com.yutang.model.background;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author fzhuoxing
 * @Time 2022/4/17 10:20 上午
 * 添加书本dto
 */

@Data
public class BookAddDTO {
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
    // 价格
    private BigDecimal price;
    // 库存
    private Integer stock;
    // 简介
    private String brief;
}
