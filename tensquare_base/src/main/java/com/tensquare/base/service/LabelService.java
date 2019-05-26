package com.tensquare.base.service;

import com.tensquare.base.dao.LableDao;
import com.tensquare.base.pojo.Label;
import com.tensquare.entity.Result;
import com.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @Author:潘佳伟
 * @Date：2019/5/18 14:31
 */
@Service
@Transactional
public class LabelService {
    @Autowired
    private LableDao lableDao;
    @Autowired
    private IdWorker idWorker;

    public List<Label> getAll() {
        List<Label> all = lableDao.findAll();
        return all;
    }

    public Label getLabelById(String labelId) {
        Optional<Label> byId = lableDao.findById(labelId);
        Label label = byId.get();
        return label;
    }

    public void deleteById(String labelId) {
        lableDao.deleteById(labelId);
    }

    public void addLable(Label label) {
        Long id = new IdWorker().nextId();
        label.setId(Long.toString(id));
        lableDao.save(label);
    }
    public void updateLabel(Label label) {
       lableDao.save(label);
    }

    public Label getLabelByName(String labelName){
        Label label = lableDao.findByLabelname(labelName);


        return label;
    }


    public List<Label> searchLabel(Label label) {
        //写业务
        //根据条件查询
            return lableDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //建立集合存储返回值
                ArrayList<Predicate> list = new ArrayList<>();
                //因为传过来的是一个label对象，里面包含很多属性，有可能一个或者N个
                if (label.getLabelname() != null && !label.getLabelname().equals("")) {
                    //写条件，如果在sql中where labelname like "%Java%"
                    Predicate predicateLabelName = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname()+"%");
                    //如果条件语句只有个条件的话，可以直接return 返回，乳沟有多个条件语句的话，需要使用集合装填
                    //return predicate;
                    list.add(predicateLabelName);
                }
                if(label.getState()!=null&&label.getState().equals("")){
                    //where state=0
                    Predicate predicateState = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicateState);
                }
                Predicate[] parr=new Predicate[list.size()];
                //把list转成数组
                list.toArray(parr);
                //由于and不接收集合但可以接收数组，因此可以将集合转成数组存储
                return criteriaBuilder.and(parr);
            }
        });
    }
    //分页查询
    public Page<Label> pageQuery(Label label, int page, int size) {
        // sql分页 实现效果是sql语句追加limit(x,y)
        PageRequest pageRequest= PageRequest.of(page-1,size);

        // findAll需要的是参数类型有一个是Pageable ,但使用它的实现类的子类 PageReqest也可以
        return lableDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                /**
                 * root 跟对象，就是要把条件封装到哪个对象中，where 类名=label.getid
                 * criteriaQuery 封装查询关键字,比如groupby order by等【但是基本不用这个对象】
                 *
                 * criteriaBuilder 用来封装条件对象
                 * */
                //new 一个集合存放所有的条件
                ArrayList<Predicate> list = new ArrayList<>();
                if(label.getLabelname()!=null&&!"".equals(label.getLabelname())) {
                    Predicate labelname = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    //上句相当于 where labelname like '%哈哈%'
                    list.add(labelname);
                }
                if(label.getState()!=null&&!"".equals(label.getState())) {
                    Predicate labelname = criteriaBuilder.equal(root.get("state").as(String.class), label.getState() );

                    list.add(labelname);
                }
                Predicate[] parr=new Predicate[list.size()];

                list.toArray(parr);
                return criteriaBuilder.and(parr);// where labelname like '%哈哈%' and state='1'
            }
        },pageRequest);

    }
}
