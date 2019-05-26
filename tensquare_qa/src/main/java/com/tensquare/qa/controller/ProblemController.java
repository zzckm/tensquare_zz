package com.tensquare.qa.controller;

import java.util.Map;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.qa.client.BaseClient;
import com.tensquare.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private BaseClient baseClient;

	/**
	 * 测试    fegin调用Base模块里的 lable 通过id 查数据
	 *
	 * Fegin使用方法总结：baseClient 这个接口是Fegin通过子模块名称命名一致，在eureka中找到tensquare-base访问路径，
	 * 再加上映射路径以及Controller层的赋值，拼接成一个完成的请求url，然后发送请求，并接收返回值Result
	 */
	@RequestMapping(value = "/aaa/{labelId}",method = RequestMethod.GET)
	public Result findByLabelId(@PathVariable String labelId){
		Result result = baseClient.findById(labelId);
		return  result;
	}


	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		String tokenUser = (String) request.getAttribute("claims_user");
		if(tokenUser==null||"".equals(tokenUser)){
			return new Result(false,StatusCode.ACCESSERROR,"权限不足，请先登录");
		}
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	//问答
	@RequestMapping(value = "/newlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result newlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){
		Page<Problem> pageData=problemService.newlist(labelid,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(pageData.getTotalElements(),pageData.getContent()));
	}
	@RequestMapping(value = "/hotlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result hotlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){
		Page<Problem> pageData=problemService.hostlist(labelid,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(pageData.getTotalElements(),pageData.getContent()));
	}
	@RequestMapping(value = "/waitlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result waitlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){
		Page<Problem> pageData=problemService.waitlist(labelid,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(pageData.getTotalElements(),pageData.getContent()));
	}

}
