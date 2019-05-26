package com.tensquare.search.controller;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {
    @Autowired
     private ArticleSearchService articleSearchService;

       @RequestMapping(method= RequestMethod.POST)
        public Result save(@RequestBody Article article){
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK,"添加成功");
        }

        @RequestMapping(value = "/{key}/{page}/{size}",method =RequestMethod.GET)
    public Result findBykey(@PathVariable String key,@PathVariable int page,@PathVariable int size){
           Page<Article> pageData=articleSearchService.findByKey(key,page,size);
           return new Result(true,StatusCode.OK,"查询成功",new PageResult(pageData.getTotalElements(),pageData.getContent()));
        }
}
