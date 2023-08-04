package com.nflj.esdemo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zp.wei
 * @date 2021/6/28 15:30
 */

@Data
public class BookVo implements Serializable {

    private static final long serialVersionUID = -2687555120176245200L;


    /**
     * id
     */
    private String id;

    /**
     * 书名
     */
    private String name;

    /**
     * 作者
     */
    private String author;

    /**
     * 价格
     */
    private Double price;

    /**
     * 简介
     */
    private String introduction;




}
