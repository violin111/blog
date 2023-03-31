package com.liu.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.dao.PageDao;
import com.liu.blog.dto.PageDTO;
import com.liu.blog.entity.Page;
import com.liu.blog.service.PageService;
import com.liu.blog.service.RedisService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.liu.blog.constant.RedisPrefixConst.PAGE_COVER;

@Service
public class PageServiceImpl extends ServiceImpl<PageDao, Page> implements PageService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private PageDao pageDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<PageVO> listPages() {
        List<PageVO> pageVOList;
        //从缓存中查找缓存信息，然后不存在则从mysql中读取，更新缓存
        Object pageList = redisService.get(PAGE_COVER);
        if(Objects.nonNull(pageList)){
            pageVOList= JSON.parseObject(pageList.toString(),List.class);
        }else {
            pageVOList= BeanCopyUtils.copyList(pageDao.selectList(null), PageVO.class);
            redisService.set(PAGE_COVER,pageVOList);
        }
        return pageVOList;
    }
}
