package com.liu.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.blog.entity.UserInfo;
import com.liu.blog.vo.UserInfoVO;
import org.springframework.stereotype.Repository;


/**
 * 用户信息
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Repository
public interface UserInfoDao extends BaseMapper<UserInfo> {
    /**
     * 更新用户资料
     * @param userInfoVO 用户资料
     */
    void updateUserInfo(UserInfoVO userInfoVO);

}
