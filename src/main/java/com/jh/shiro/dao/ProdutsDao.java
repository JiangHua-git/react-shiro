/**
 * @title :  ProdutsDao.java
 * @author: jianghua
 * @create: 2020/3/27 21:55
 * @modify:
 * @description : 商品Dao
 * @version: 1.0
 */
package com.jh.shiro.dao;

import com.jh.shiro.entity.ProdutsBean;
import com.jh.shiro.entity.dto.ProdutsDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * @description：商品Dao
 * @author: jianghua
 * @version: 1.0
 * @date: 2020/3/27 21:56
 */
public interface ProdutsDao extends JpaRepository<ProdutsBean, Integer>, JpaSpecificationExecutor<ProdutsBean> {

    /**
     * 根据商品名称查询商品
     *
     * @param produtsName 商品名称
     * @return List<ProdutsBean>
     */
    @Select("select count(id) cnt from produts where name like #{produtsName}")
    List<ProdutsBean> queryProdutsInfoByName(@Param("produtsName") String produtsName);
}
