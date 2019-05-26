package com.tensquare.qa.client;

import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 这个接口是Fegin通过子模块名称，在eureka中找到tensquare-base访问路径，再加上映射路径以及Controller层的赋值，拼接成一个完成的请求url
 */
@FeignClient("tensquare-base")
public interface BaseClient {

    @RequestMapping(value="/label/{id}",method= RequestMethod.GET)
                                //！！！PathVariable（"id"） 需要和映射的路径的/｛id｝保持名称一致，Fegin请求是核对｛id｝与“id”的
      public Result findById(@PathVariable("id") String id);
}
