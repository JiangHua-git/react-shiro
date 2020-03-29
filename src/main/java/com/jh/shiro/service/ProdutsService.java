/**
 * @title :  Produts.java
 * @author: jianghua
 * @create: 2020/3/27 22:13
 * @modify:
 * @description : 商品Service
 * @version: 1.0
 */
package com.jh.shiro.service;

import com.jh.shiro.entity.ProdutsBean;
import com.jh.shiro.entity.dto.ProdutsDto;
import com.jh.shiro.util.Result;

/**
 * @description：商品Service
 * @author: jianghua
 * @version: 1.0
 * @date: 2020/3/27 22:13
 */
public interface ProdutsService extends CommonService<ProdutsBean> {

    /**
     * 更具商品名称查询商品
     * @param produtsName
     * @return: int
     */
    int queryProdutsInfoByName(String produtsName);

    /**
     * 更具商品名称查询商品
     * @param produtsDto
     * @return: int
     */
    Result listProdutsInfo(ProdutsDto produtsDto);
}
