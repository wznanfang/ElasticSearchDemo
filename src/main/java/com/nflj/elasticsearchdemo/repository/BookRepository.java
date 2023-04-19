package com.nflj.elasticsearchdemo.repository;

import com.nflj.elasticsearchdemo.domain.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zp.wei
 * @date 2021/6/28 15:34
 */
@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String> {
}

