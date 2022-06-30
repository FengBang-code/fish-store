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
    @TableName("account_statement")
public class AccountStatement implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 流水号
     */
      @TableField("account_num")
    private String accountNum;

      /**
     * 订单号
     */
      @TableField("order_num")
    private String orderNum;

      /**
     * 金额
     */
      @TableField("account_amount")
    private BigDecimal accountAmount;

      /**
     * 流水备注
     */
      @TableField("account_remark")
    private String accountRemark;

      /**
     * 支付类型（0为本站支付，1为网银支付，2为现金支付,3为退款）
     */
      @TableField("pay_type")
    private String payType;

      /**
     * 客户名
     */
      @TableField("cus_name")
    private String cusName;

    /**
     * 客户id
     */
    @TableField("cus_num")
      private String cusNum;
      /**
     * 创建时间
     */
      @TableField("create_time")
    private LocalDateTime createTime;

      /**
     * 逻辑删除
     */
      @TableLogic
      @TableField("del_flag")
    private String delFlag;


}
