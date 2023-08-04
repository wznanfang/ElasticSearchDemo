package com.nflj.esdemo;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.nflj.esdemo.domain.Book;
import com.nflj.esdemo.domain.BookInfo;
import com.nflj.esdemo.repository.BookRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ElasticSearchDemoApplicationTests {

    @Resource
    private BookRepository bookRepository;
    @Resource
    private ElasticsearchClient client;


    @Test
    void jpaSave() {
        List<Book> list = new ArrayList<>();
        for (int i = 1; i < 101; i++) {
            Book book = new Book();
            book.setId(String.valueOf(i));
            book.setName("人间游途" + i);
            BookInfo bookInfo = new BookInfo();
            bookInfo.setAuthor("不逆" + i);
            bookInfo.setPrice((double) i);
            bookInfo.setDesc("一本介绍旅途风景的书" + i);
            book.setBookInfo(bookInfo);
            list.add(book);
        }
        bookRepository.saveAll(list);
    }

    @Test
    void jpaUpdate() {
        Optional<Book> optional = bookRepository.findById("1");
        Book book = optional.orElse(null);
        BookInfo bookInfo = book.getBookInfo();
        bookInfo.setPrice(1.0);
        book.setBookInfo(bookInfo);
        bookRepository.save(book);
    }

    @Test
    void jpaDelete() {
//        bookRepository.deleteById("1");
        bookRepository.deleteAll();
    }

    @Test
    void jpaFindById() {
        Optional<Book> optional = bookRepository.findById("1");
        System.out.println(optional.orElse(null));
    }

    @Test
    void jpaFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> page = bookRepository.findAll(pageable);
        page.getContent().forEach(System.out::println);
        System.out.println(page.getContent().size());
    }

    @Test
    void clientFindAll() throws IOException {
        //精确匹配
//        Query name = MatchQuery.of(m -> m.field("name").query("人间游途1"))._toQuery();
//        Query author = MatchQuery.of(m -> m.field("bookInfo.author").query("不逆1"))._toQuery();
        //模糊匹配,通配符模式
//        Query name = WildcardQuery.of(m -> m.field("name").value("人间*"))._toQuery();
//        Query author = WildcardQuery.of(m -> m.field("bookInfo.author").value("不逆*"))._toQuery();
        //模糊匹配，容错率模式，fuzziness：即允许在查询条件的基础上误差数
//        Query name = FuzzyQuery.of(f -> f.field("name").value("人间游途").fuzziness("1"))._toQuery();
//        Query author = FuzzyQuery.of(f -> f.field("bookInfo.author").value("不逆").fuzziness("1"))._toQuery();
        // 组装查询条件
//        Query query = Query.of(q -> q.bool(b -> b.must(name, author)));
        //查询id在list中的数据
//        Query query = IdsQuery.of(id->id.values("1","2"))._toQuery();
        //查询价格大于100,小于200的数据
        Query query = RangeQuery.of(f -> f.field("bookInfo.price").gt(JsonData.of(1)).lt(JsonData.of(100)))._toQuery();
        //进行查询
        SearchResponse<Book> result = client.search(s -> s
                        .index("book")
                        .query(query)
                        .aggregations("bookInfo.avgPrice", a -> a.avg(avg -> avg.field("bookInfo.price"))) //求平均
                        .aggregations("bookInfo.sumPrice", total -> total.sum(sum -> sum.field("bookInfo.price"))) //求和
                        .from(0)
                        .size(10)
                        .sort(f -> f.field(o -> o.field("bookInfo.price").order(SortOrder.Desc)))
                , Book.class);
        List<Hit<Book>> hits = result.hits().hits();
        for (Hit<Book> hit : hits) {
            Book book = hit.source();
            System.out.println(book);
        }
        System.err.println("总条数：" + result.hits().total().value());
        System.err.println("平均价格：" + result.aggregations().get("bookInfo.avgPrice").avg());
        System.err.println("总价：" + result.aggregations().get("bookInfo.sumPrice").sum());
    }

}
