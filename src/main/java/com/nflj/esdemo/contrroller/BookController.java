package com.nflj.esdemo.contrroller;

import com.nflj.esdemo.domain.Book;
import com.nflj.esdemo.repository.BookRepository;
import com.nflj.esdemo.vo.BookVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        BeanUtils.copyProperties(bookVo, book);
        return bookRepository.save(book);
    }


    @GetMapping("/findAll")
    public Object findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        return bookRepository.findAll(pageable);
    }


}
