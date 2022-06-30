package com.yutang.service.impl;

import com.yutang.entity.Book;
import com.yutang.mapper.BookMapper;
import com.yutang.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fzhuoxing
 * @since 2022-04-14
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

}
