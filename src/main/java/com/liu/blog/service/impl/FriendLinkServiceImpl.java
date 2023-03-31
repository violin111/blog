package com.liu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.dao.FriendLinkDao;
import com.liu.blog.dto.FriendLinkBackDTO;
import com.liu.blog.dto.FriendLinkDTO;
import com.liu.blog.entity.FriendLink;
import com.liu.blog.service.FriendLinkService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.util.PageUtils;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkDao, FriendLink> implements FriendLinkService {
    @Autowired
    private FriendLinkDao friendLinkDao;
    @Override
    public PageResult<FriendLinkBackDTO> listFriendLinkDTO(ConditionVO conditionVO) {
        Page<FriendLink> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        Page<FriendLink> friendLinkPage = friendLinkDao.selectPage(page, new LambdaQueryWrapper<FriendLink>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), FriendLink::getLinkName, conditionVO.getKeywords()));
        List<FriendLinkBackDTO> friendLinkBackDTOS = BeanCopyUtils.copyList(friendLinkPage.getRecords(), FriendLinkBackDTO.class);
        return new PageResult<>(friendLinkBackDTOS,(int)friendLinkPage.getTotal());
    }
}
