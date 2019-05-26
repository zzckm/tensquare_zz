package com.tensquare.base.controller;

import com.tensquare.base.service.LabelService;
import com.tensquare.base.pojo.Label;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:潘佳伟
 * @Date：2019/5/18 14:39
 */
@RestController
@RequestMapping("/label")
public class LabelController {
    @Autowired
    private LabelService labelService;

    @RequestMapping(method = RequestMethod.GET)
    public Result getAll() {
        List<Label> all = labelService.getAll();
        return new Result(true, StatusCode.OK, "success", all);
    }

    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public Result getById(@PathVariable String labelId) {
        Label label = labelService.getLabelById(labelId);
        return new Result(true, StatusCode.OK, "success", label);
    }

    @RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId) {
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "success");
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result addLabel(@RequestBody Label label) {
        labelService.addLable(label);
        return new Result(true, StatusCode.OK, "success");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result updateLabel(@RequestBody Label label) {
        labelService.updateLabel(label);
        return new Result(true, StatusCode.OK, "success");

    }
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result searchLabel(@RequestBody Label label){
        List<Label>  list=labelService.searchLabel(label);

        return new Result(true,StatusCode.OK,"查询成功",list);
    }
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Label label,@PathVariable int page,@PathVariable int size){


        Page<Label> pageData=labelService.pageQuery(label,page,size);

        PageResult<Label> labelPageResult = new PageResult<>(pageData.getTotalElements(), pageData.getContent());
        return new Result(true,StatusCode.OK,"查询分页数据成功",labelPageResult);
    }





   /* @RequestMapping(value = "/labelname/{labelName}", method = RequestMethod.GET)
    public Result getByName(@PathVariable String labelName) {
        Label label = labelService.getLabelByName(labelName);
        return new Result(true, StatusCode.OK, "success", label);
    }*/
}
