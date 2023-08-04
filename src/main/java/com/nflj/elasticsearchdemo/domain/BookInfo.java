package com.nflj.esdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author zp.wei
 * @date 2023/8/4 9:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookInfo implements Serializable {

    private static final long serialVersionUID = 8498501025632950281L;

    /**
     * 作者
     */
    @Field(type = FieldType.Keyword)
    private String author;

    /**
     * 价格
     */
    @Field(type = FieldType.Double)
    private Double price;

    /**
     * 简介
     */
    @Field(type = FieldType.Text)
    private String desc;


}
