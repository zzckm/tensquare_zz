package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    //最新回答
    @Query(value = "select  * from tb_problem,tb_pl where  tb_problem.id=tb_pl.problemid and labelid=? order by replytime DESC ",nativeQuery = true)
    public Page<Problem> newlist(String labelId, Pageable pageable);
    //热门回答
    @Query(value = "select  * from tb_problem,tb_pl where  tb_problem.id=tb_pl.problemid and labelid=? order by reply DESC",nativeQuery = true)
    public Page<Problem> hostlist(String labelId, Pageable pageable);
    //等待回答
    @Query(value = "select  * from tb_problem,tb_pl where  tb_problem.id=tb_pl.problemid and labelid=? and reply=0 order by createtime DESC",nativeQuery = true)
    public Page<Problem> waitlist(String labelId, Pageable pageable);

}
