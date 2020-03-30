/**
 * @title :  ProdutsServiceImpl.java
 * @author: jianghua
 * @create: 2020/3/27 22:14
 * @modify:
 * @description : 商品Service实现类
 * @version: 1.0
 */
package com.jh.shiro.service.impl;

import com.jh.shiro.dao.ProdutsDao;
import com.jh.shiro.entity.ProdutsBean;
import com.jh.shiro.entity.dto.ProdutsDto;
import com.jh.shiro.service.ProdutsService;
import com.jh.shiro.util.Result;
import com.jh.shiro.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description：商品Service实现类
 * @author: jianghua
 * @version: 1.0
 * @date: 2020/3/27 22:14
 */
@Slf4j
@Service
public class ProdutsServiceImpl implements ProdutsService {

    @Resource
    private ProdutsDao produtsDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result add(ProdutsBean produtsBean) {
        Result result;
        int cnt;
        try {
            cnt = queryProdutsInfoByName(produtsBean.getName());
            if (cnt > 0) {
                result = new Result(ResultCode.FAIL);
                result.setMessage("商品已存在");
            } else {
                produtsDao.save(produtsBean);
                result = new Result(ResultCode.SUCCESS);
            }
        } catch (Exception e) {
            log.error("保存失败：{}", e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public Result deleteById(Integer id) {
        return null;
    }

    @Override
    public Result deleteAll() {
        return null;
    }

    @Override
    public Result update(ProdutsBean produtsBean) {
        return null;
    }

    @Override
    public Result get(String id) {
        Result result;
        Optional produtsBean;
        try {
            produtsBean = produtsDao.findById(Integer.parseInt(id));
            result = new Result(ResultCode.SUCCESS);
            result.setData(produtsBean);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public Result list(Integer id) {
        return null;
    }

    @Override
    public Result list() {
        Result result;
        List<ProdutsBean> list;
        try {
            list = produtsDao.findAll();
            result = new Result(ResultCode.SUCCESS);
            result.setList(list);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public int queryProdutsInfoByName(String produtsName) {
        List<ProdutsBean> list;
        try {
            list = produtsDao.queryProdutsInfoByName(produtsName);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            throw e;
        }
        return list.size();
    }

    @Override
    public Result listProdutsInfo(ProdutsDto produtsDto) {
        Result result;
        int page = produtsDto.getPage() == null ? 0 : produtsDto.getPage() - 1;
        int size = produtsDto.getSize() == null ? 100 : produtsDto.getSize();
        Page<ProdutsBean> produtsBeans;
        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(page, size, sort);
            result = new Result(ResultCode.SUCCESS);
            produtsBeans = produtsDao.findAll(pageable);
            result.setList(produtsBeans.getContent());
            result.setTotalCount((int) produtsBeans.getTotalElements());
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            throw e;
        }
        return result;
    }
}
