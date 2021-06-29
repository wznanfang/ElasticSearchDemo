package com.nflj.elasticsearchdemo.contrroller;

import com.nflj.elasticsearchdemo.domain.Book;
import com.nflj.elasticsearchdemo.repository.BookRepository;
import com.nflj.elasticsearchdemo.vo.BookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author zp.wei
 * @date 2021/6/28 15:39
 */

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;


    @PostMapping("/save")
    public Object save(@RequestBody BookVo bookVo) {
        Book book = new Book();
        if (StringUtils.hasLength(bookVo.getName())) {
            book.setName(book.getName());
        }
        if (StringUtils.hasLength(bookVo.getAuthor())) {
            book.setAuthor(book.getAuthor());
        }
        if (StringUtils.hasLength(bookVo.getPrice().toString())) {
            book.setPrice(book.getPrice());
        }
        if (StringUtils.hasLength(bookVo.getIntroduction())) {
            book.setIntroduction(book.getIntroduction());
        }
        Book book1 = bookRepository.save(book);
        return book1;
    }


    @GetMapping("/findAll")
    public Object findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> page = bookRepository.findAll(pageable);
        return page;
    }


}
