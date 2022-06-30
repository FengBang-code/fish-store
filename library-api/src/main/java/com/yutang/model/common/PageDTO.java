package com.yutang.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 11:34 上午
 */

@ApiModel
@Data
public class PageDTO {
    public final static int MAX_PAGESIZE = 100;
    public final static int DEFAULT_PAGESIZE = 10;

    @ApiModelProperty(value = "页码(不传递默认为1)",example = "1")
    private long current = 1;
    @ApiModelProperty(value = "每页显示的记录数(不传递默认为10)",example = "10")
    private long pageSize = DEFAULT_PAGESIZE;
}
