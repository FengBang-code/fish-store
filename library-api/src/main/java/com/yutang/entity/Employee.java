package com.yutang.entity;

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
    @TableName("employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 员工名
     */
      @TableField("emp_name")
    private String empName;

      /**
     * 工号
     */
      @TableField("emp_num")
    private String empNum;

      /**
     * 密码
     */
      @TableField("emp_pass")
    private String empPass;

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
     * 是否启用(0未启用，1启用)
     */
      @TableField("enabled")
    private Boolean enabled;

      /**
     * ‘0’为员工，’1’为管理员
     */
      @TableField("state")
    private String state;

      /**
     * 逻辑删除
     */
      @TableLogic
      @TableField("del_flag")
    private String delFlag;


}
