package com.tensquare.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
                   // 索引库名称
@Document(indexName = "tensquare",type = "article")
public class Article {
    @Id
    private  String id;//ID
    /**
     * 是否索引，是否允许被执行搜索
     * 是否分词，就表示搜索的时候是整体匹配还是单词匹配
     * 是否存储  是否在页面上显示
     */                            //已包含内容的分词方式 //输入搜索关键词的分词方式
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;//标题

    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;//正文内容

    private  String state;//审核状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
