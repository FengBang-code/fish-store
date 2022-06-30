package com.yutang.mapper;

import com.yutang.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yutang.model.background.BookUpdateDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fzhuoxing
 * @since 2022-04-14
 */
public interface BookMapper extends BaseMapper<Book> {
    @Update("update book set enabled = true ,enabled_time = #{time} where id = #{id}")
    void enabledAction(String time, Long id);

    void updateBook(@Param("model") BookUpdateDTO model);

    @Select("select book_name from book where id = #{id}")
    String findBookNameById(Long id);
}
