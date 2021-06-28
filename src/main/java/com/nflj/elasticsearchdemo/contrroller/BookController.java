package com.nflj.elasticsearchdemo.contrroller;

import com.nflj.elasticsearchdemo.domain.Book;
import com.nflj.elasticsearchdemo.repository.BookRepository;
import com.nflj.elasticsearchdemo.vo.BookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zp.wei
 * @date 2021/6/28 15:39
 */

@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;


    @PostMapping("/save")
    public Object save(BookVo bookVo) {
        Book book = new Book();
        if (!StringUtils.hasLength(bookVo.getName())) {
            book.setName(book.getName());
        }
        if (!StringUtils.hasLength(bookVo.getAuthor())) {
            book.setAuthor(book.getAuthor());
        }
        if (!StringUtils.hasLength(bookVo.getPrice().toString())) {
            book.setPrice(book.getPrice());
        }
        if (!StringUtils.hasLength(bookVo.getIntroduction())) {
            book.setIntroduction(book.getIntroduction());
        }
        Book a = bookRepository.save(book);
        return a;
    }


}
