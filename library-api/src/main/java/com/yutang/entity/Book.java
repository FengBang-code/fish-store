package com.yutang.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author fzhuoxing
 * @since 2022-04-14
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * isbn号
     */
      @TableField("isbn")
    private String isbn;

      /**
     * 图书图片url
     */
      @TableField("pic_url")
    private String picUrl;

      /**
     * 书名
     */
      @TableField("book_name")
    private String bookName;

      /**
     * 作者
     */
      @TableField("author")
    private String author;

      /**
     * 出版社
     */
      @TableField("publication")
    private String publication;

      /**
     * 书类
     */
      @TableField("category")
    private String category;

      /**
     * 启用（1启用，0未启用）
     */
      @TableField("enabled")
    private Boolean enabled;

      /**
     * 启用时间
     */
      @TableField("enabled_time")
    private LocalDateTime enabledTime;

      /**
     * 书本价格
     */
      @TableField("price")
    private BigDecimal price;

      /**
     * 库存
     */
      @TableField("stock")
    private Integer stock;

      /**
     * 简介
     */
      @TableField("brief")
    private String brief;

      /**
     * 更新时间
     */
      @TableField("update_time")
    private LocalDateTime updateTime;

      /**
     * 逻辑删除
     */
      @TableLogic
      @TableField("del_flag")
    private String delFlag;

      /**
     * 不启用原因
     */
      @TableField("not_enabled_reason")
    private String notEnabledReason;

      /**
     * 创建时间
     */
      @TableField("create_time")
    private LocalDateTime createTime;


}
