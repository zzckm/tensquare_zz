package com.tensquare.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletRequest;

import com.tensquare.util.IdWorker;
import com.tensquare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void add(User user) {
		user.setId( idWorker.nextId()+"" );
		//密码bCrypt加密
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setFollowcount(0);//关注数
		user.setFanscount(0);//粉丝数        
		user.setOnline(0L);//在线时长        
		user.setRegdate(new Date());//注册日期        
		user.setUpdatedate(new Date());//更新日期        
		user.setLastdate(new Date());//最后登陆日期
		userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除 (必须有admin角色才可以删除)
	 * @param id
	 */
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private HttpServletRequest request;

	public void deleteById(String id) {
		String token = (String)request.getAttribute("claims_admin");
		if(token==null||"".equals(token)){
			throw  new RuntimeException("权限不足，不是管理员");
		}
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	/**
	 * 发送短信验证码
	 * @param moblie
	 */
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendSms(String moblie) {
		//生成6位随机数
		/*Random random = new Random();
		int max=999999;//最大数
		int min=100000;//最小数
		int code=random.nextInt(max);
		if(code<min){
			code=code+min;
		}*/
		//不使用上述方法，太长，使用lang3的工具对象，6代表六位数的随机数
		String checkCode = RandomStringUtils.randomNumeric(6);
		//向redis缓存中放一份
		redisTemplate.opsForValue().set("checkcode_"+moblie,checkCode,6, TimeUnit.HOURS);
		//向用户发一份 传输到消息队列中
		Map<String,String> map = new HashMap();
		map.put("mobile",moblie);
		map.put("checkcode",checkCode);
		rabbitTemplate.convertAndSend("sms",map);
		//控制台显示一份（方便测试）
		System.out.println("验证码为： "+checkCode   );
	}

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */

	public User login(User user) {
		User userByMobile = userDao.findByMobile(user.getMobile());
		if(userByMobile!=null&&bCryptPasswordEncoder.matches(user.getPassword(),userByMobile.getPassword())){
			return user;
		}
		return null;
	}
}
