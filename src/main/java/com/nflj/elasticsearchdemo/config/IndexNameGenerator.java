package com.nflj.elasticsearchdemo.config;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author zp.wei
 * @date 2023/4/21 15:31
 */
@Component("indexNameGenerator")
public class IndexNameGenerator {

    /**
     * 根据时间动态生成索引名
     * @return
     */
    public String commonIndex() {
        //根据日期生成index
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        return date;
    }
}
