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
@TableName("order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

      /**
     * 订单号
     */
        @TableField("order_num")
        private String orderNum;

      /**
     * 订单备注
     */
        @TableField("order_remark")
        private String orderRemark;

      /**
     * 书本ids
     */
        @TableField("book_ids")
        private String bookIds;

      /**
     * 应付金额
     */
        @TableField("order_amount")
        private BigDecimal orderAmount;

      /**
     * 订单状态(0为完成支付，1为欠款，2退款完成)
     */
      @TableField("order_state")
    private String orderState;

      /**
     * 欠款金额
     */
      @TableField("debt")
    private BigDecimal debt;

      /**
     * 创建时间
     */
      @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 下单客户
     */
      @TableField("cus_name")
      private String cusName;

      /**
     * 逻辑删除
     */
      @TableLogic
      @TableField("del_flag")
      private String delFlag;

      /**
       * 下单客户的id
       */
      @TableField("cus_id")
      private Long cusId;

}
