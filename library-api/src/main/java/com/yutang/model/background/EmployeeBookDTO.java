package com.yutang.model.background;

import com.yutang.model.common.PageDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author fzhuoxing
 * @Time 2022/4/16 4:43 下午
 */

@Data
public class EmployeeBookDTO extends PageDTO implements Serializable {
    // isbn
    private String isbn;
    // 书名
    private String bookName;
    // 作者
    private String author;
    // 出版社
    private String publication;
    // 书类
    private String category;
    // 是否启用
    private Boolean enabled;
}
