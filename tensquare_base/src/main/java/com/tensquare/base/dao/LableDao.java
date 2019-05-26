package com.tensquare.base.dao;

import com.tensquare.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author:潘佳伟
 * @Date：2019/5/18 14:24
 */
public interface LableDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {
    Label findByLabelname(String lableName);

}
