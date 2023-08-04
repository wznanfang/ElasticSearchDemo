package com.nflj.elasticsearchdemo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author zp.wei
 * @date 2021/6/28 15:30
 */

@Data
@Document(indexName = "book")
//@Document(indexName = "book_#{@indexNameGenerator.commonIndex()}") //动态创建索引名字
public class Book implements Serializable {

    private static final long serialVersionUID = 8498501025632950288L;

    private String _class;

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 书名
     */
    @Field(type = FieldType.Keyword)
    private String name;

    /**
     * 书籍信息
     */
    @Field(type = FieldType.Object)
    private BookInfo bookInfo;


}
