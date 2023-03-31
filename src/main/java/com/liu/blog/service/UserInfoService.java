package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.UserOnlineDTO;
import com.liu.blog.entity.UserInfo;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.UserInfoVO;
import com.liu.blog.vo.UserRoleVO;
import org.springframework.stereotype.Service;


public interface UserInfoService extends IService<UserInfo> {
    /**
     * 更新用户资料
     * @param userInfoVO 用户资料
     */
    void updateUserInfo(UserInfoVO userInfoVO);


    /**
     * 更新用户角色
     *
     * @param userRoleVO 更新用户角色
     */
    void updateUserRole(UserRoleVO userRoleVO);
    /**
     * 查看在线用户列表
     *
     * @param conditionVO 条件
     * @return 在线用户列表
     */
    PageResult<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO);
    /**
     * 下线用户
     *
     * @param userInfoId 用户信息id
     */
    void removeOnlineUser(Integer userInfoId);
}
