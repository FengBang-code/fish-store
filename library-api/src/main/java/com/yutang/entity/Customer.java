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
    @TableName("customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 客户名
     */
      @TableField("cus_name")
    private String cusName;

      /**
     * 账号
     */
      @TableField("cus_num")
    private String cusNum;

      /**
     * 密码
     */
      @TableField("cus_pass")
    private String cusPass;

      /**
     * 创建时间
     */
      @TableField("create_time")
    private LocalDateTime createTime;

      /**
     * 更新时间
     */
      @TableField("update_time")
    private LocalDateTime updateTime;

      /**
     * 最后登录时间
     */
      @TableField("last_login")
    private LocalDateTime lastLogin;

      /**
     * 手机号
     */
      @TableField("phone")
    private String phone;

      /**
     * 启用(1)
     */
      @TableField("enabled")
    private Boolean enabled;

      /**
     * 身份证号
     */
      @TableField("identity")
    private String identity;

      /**
     * 本站余额
     */
      @TableField("balance")
    private BigDecimal balance;

      /**
     * 常用地址
     */
      @TableField("address")
    private String address;

      /**
     * 逻辑删除(1删除)
     */
      @TableLogic
      @TableField("del_flag")
    private String delFlag;


}
