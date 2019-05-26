package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import com.tensquare.util.IdWorker;
import org.apache.commons.lang3.time.DateFormatUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class SpitService {

        @Autowired
        private SpitDao spitDao;
        @Autowired
        private IdWorker idWorker;
        @Autowired
        private MongoTemplate mongoTemplate;

        //查询全部
        public List<Spit> findAll() {
            return spitDao.findAll();
        }

        //根据主键查询
        public Spit findById(String id) {
            return spitDao.findById(id).get();
        }
        //新增(发布吐槽)
        public void save(Spit spit) {
            spit.set_id(idWorker.nextId() + "");
            spit.setPublishtime(new Date());
            //获取当前时间
            //DateFormatUtils.format(new Date(),"yyyy-MM-dd");
            spit.setVisits(0);//浏览量
            spit.setShare(0);//分享数
            spit.setThumbup(0);//点赞数
            spit.setComment(0);//回复数
            spit.setState("1");//状态
            if(spit.getParentid()!=null && !"".equals(spit.getParentid())){
            // 如果存在上级ID,评论
                Query query=new Query();//添加条件
                //修改id为spit父类id的数据
                query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
                Update update=new Update();
                update.inc("comment",1);
                mongoTemplate.updateFirst(query,update,"spit");
            }
            spitDao.save(spit);
        }
        //修改
        public void update(Spit spit) {
            spitDao.save(spit);
        }
        //删除
        public void deleteById(String id) {
            spitDao.deleteById(id);
        }
    //根据上级id查询吐槽信息
    public Page<Spit> findByParentid(String parentid, int page, int size){
        Pageable pageable= PageRequest.of(page-1,size);
        return spitDao.findByParentid(parentid, pageable);
    }
    //吐槽点赞数
    public void thumbup(String spitId) {
        Spit spit = spitDao.findById(spitId).get();
        spit.setThumbup(spit.getThumbup()==null?1:spit.getThumbup()+1);
        spitDao.save(spit);
    }

}

