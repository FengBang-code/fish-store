package com.yutang.model.background;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : fzhuoxing
 * @date : 2022-06-23 12:41
 * 销售记录圆饼图、柱状图
 **/

@Data
public class BookRecordVO {

    // 柱装图list
    List<Integer> barList;
    // 圆饼图list
    List<BigDecimal> cirList;

    List<BookTopVO> bookTopVOList;

    // 一周的时间数组
    List<String> times;
}
