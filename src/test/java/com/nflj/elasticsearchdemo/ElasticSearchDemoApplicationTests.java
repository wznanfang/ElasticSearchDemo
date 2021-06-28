package com.nflj.elasticsearchdemo;

import com.nflj.elasticsearchdemo.domain.Book;
import com.nflj.elasticsearchdemo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@SpringBootTest
class ElasticSearchDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private BookRepository bookRepository;

    @Test
    void save() {
        Book book = new Book();
        book.setName("时间");
        book.setAuthor("wzp");
        book.setPrice(96.58);
        book.setIntroduction("超级牛逼的一本书");
        Book a = bookRepository.save(book);
        System.out.println(a);
    }

    @Test
    void delete() {
        bookRepository.deleteById("7C2dUXoBLSM8rIvWaVFz");
    }

    @Test
    void Update() {
        Optional<Book> optional = bookRepository.findById("7S2gUXoBLSM8rIvW7lFj");
        Book book = optional.orElse(null);
        book.setName("时间11111");
        book.setAuthor("wzp1");
        book.setPrice(96.58);
        book.setIntroduction("超级牛逼的一本书aaaaaaaaaaa");
        Book a = bookRepository.save(book);
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> page = bookRepository.findAll(pageable);
        page.getContent().forEach(book -> {
            System.out.println(book);
        });
    }

}
