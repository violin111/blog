package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.PageDTO;
import com.liu.blog.entity.Page;
import com.liu.blog.vo.PageVO;

import java.util.List;

public interface PageService extends IService<Page> {
    /**
     * 获取页面列表
     *
     * @return {@link List <PageVO>} 页面列表
     */
    List<PageVO> listPages();
}
