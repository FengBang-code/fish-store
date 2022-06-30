package com.yutang.model.background;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 2:45 下午
 * 新增工作人员DTO
 */

@Data
public class EmployeeDTO {
    private String empName;
    private String empNum;
    private String empPass;
    private String state;
}
