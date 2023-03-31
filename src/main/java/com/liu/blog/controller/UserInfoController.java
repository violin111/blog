package com.liu.blog.controller;

import com.liu.blog.annotation.OptLog;
import com.liu.blog.dto.UserInfoDTO;
import com.liu.blog.dto.UserOnlineDTO;
import com.liu.blog.service.UserInfoService;
import com.liu.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.liu.blog.constant.OptTypeConst.UPDATE;

@Api("用户信息模块")
@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     *
     * @param userInfoVO
     * @return
     */
    @ApiOperation("更新用户信息")
    @PutMapping("/users/info")
    public Result<?> updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO){
        userInfoService.updateUserInfo(userInfoVO);
        return Result.ok();
    }

    /**
     * 修改用户角色
     *
     * @param userRoleVO 用户角色信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户角色")
    @PutMapping("/admin/users/role")
    public Result<?> updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        userInfoService.updateUserRole(userRoleVO);
        return Result.ok();
    }

    /**
     * 查看在线用户
     *
     * @param conditionVO 条件
     * @return {@link Result<UserOnlineDTO>} 在线用户列表
     */
    @ApiOperation(value = "查看在线用户")
    @GetMapping("/admin/users/online")
    public Result<PageResult<UserOnlineDTO>> listOnlineUsers(ConditionVO conditionVO) {
        return Result.ok(userInfoService.listOnlineUsers(conditionVO));
    }

    /**
     * 下线用户
     *
     * @param userInfoId 用户信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "下线用户")
    @DeleteMapping("/admin/users/{userInfoId}/online")
    public Result<?> removeOnlineUser(@PathVariable("userInfoId") Integer userInfoId) {
        userInfoService.removeOnlineUser(userInfoId);
        return Result.ok();
    }

}
