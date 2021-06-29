package com.nflj.elasticsearchdemo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @author zp.wei
 * @date 2021/6/28 15:30
 */

@Data
@Document(indexName = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 8498501025632950288L;

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 书名
     */
    @Field
    private String name;

    /**
     * 作者
     */
    @Field
    private String author;

    /**
     * 价格
     */
    @Field
    private Double price;

    /**
     * 简介
     */
    @Field
    private String introduction;




}
