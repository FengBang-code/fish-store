package com.yutang.model.background;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author fzhuoxing
 * @Time 2022/6/23 4:24 下午
 * top10图书vo
 */

@Data
public class BookTopVO {

    private String bookName;
    private List<Integer> bookNum;
//    private Date dateTime;

}
