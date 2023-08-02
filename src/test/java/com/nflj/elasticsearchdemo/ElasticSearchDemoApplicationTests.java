package com.nflj.esdemo;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.AvgAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.nflj.esdemo.domain.Book;
import com.nflj.esdemo.repository.BookRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ESDemoApplicationTests {

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
            book.setName("时间" + i);
            book.setAuthor("wzp" + i);
            book.setPrice(100.00 + i);
            book.setDesc("超级牛逼的一本书" + i);
            list.add(book);
        }
        bookRepository.saveAll(list);
    }

    @Test
    void jpaUpdate() {
        Optional<Book> optional = bookRepository.findById("1");
        Book book = optional.orElse(null);
        book.setDesc("超级牛逼的一本书1");
        bookRepository.save(book);
    }

    @Test
    void jpaDelete() {
        bookRepository.deleteById("102");
    }

    @Test
    void jpaFindById() {
        Optional<Book> optional = bookRepository.findById("101");
        System.out.println(optional.orElse(null));
    }

    @Test
    void jpaFindAll() {
        Pageable pageable = PageRequest.of(0, 200000);
        Page<Book> page = bookRepository.findAll(pageable);
        page.getContent().forEach(System.out::println);
        System.out.println(page.getContent().size());
    }

    @Test
    void clientFindAll() throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //精确匹配
//        Query name = MatchQuery.of(m -> m.field("name").query("时间1"))._toQuery();
//        Query author = MatchQuery.of(m -> m.field("author").query("wzp1"))._toQuery();
        //模糊匹配,通配符模式
//        Query name = WildcardQuery.of(m -> m.field("name").value("时间*"))._toQuery();
//        Query author = WildcardQuery.of(m -> m.field("author").value("wzp*"))._toQuery();
        //模糊匹配，容错率模式，fuzziness：即允许在查询条件的基础上误差数
//        Query name = FuzzyQuery.of(f -> f.field("name").value("时间").fuzziness("1"))._toQuery();
//        Query author = FuzzyQuery.of(f -> f.field("author").value("wzp").fuzziness("1"))._toQuery();
        // 组装查询条件
//        Query query = Query.of(q -> q.bool(b -> b.must(name, author)));
        //查询id在list中的数据
//        Query query = IdsQuery.of(id->id.values("1","2"))._toQuery();
        //查询价格大于100,小于200的数据
        Query query = RangeQuery.of(f -> f.field("price").gte(JsonData.of(100)).lte(JsonData.of(200)))._toQuery();
        //进行查询
        SearchResponse<Book> result = client.search(s -> s.index("book")
                .query(query)
                .aggregations("price", a -> a.avg(avg -> avg.field("price"))) //求平均数
                .from(0)
                .size(10)
                .sort(f -> f.field(o -> o.field("price").order(SortOrder.Asc))), Book.class);
        List<Hit<Book>> hits = result.hits().hits();
        for (Hit<Book> hit : hits) {
            Book book = hit.source();
            System.out.println(book);
        }
        System.err.println("总条数：" + result.hits().total().value());
        System.err.println("平均价格：" + result.aggregations().get("price").avg());
    }

}
