package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dao.FriendLinkDao;
import com.liu.blog.dto.FriendLinkBackDTO;
import com.liu.blog.dto.FriendLinkDTO;
import com.liu.blog.entity.FriendLink;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;

import java.util.List;

public interface FriendLinkService extends IService<FriendLink> {
    /**
     * 查看后台友链列表
     *
     * @param condition 条件
     * @return 友链列表
     */
    PageResult<FriendLinkBackDTO> listFriendLinkDTO(ConditionVO condition);
}
