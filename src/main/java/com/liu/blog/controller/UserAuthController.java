package com.liu.blog.controller;

import com.liu.blog.dto.UserAreaDTO;
import com.liu.blog.dto.UserBackDTO;
import com.liu.blog.service.UserAuthService;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.PasswordVO;
import com.liu.blog.vo.Result;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户账号控制器
 */
@RestController
public class UserAuthController {
    @Autowired
    private UserAuthService userAuthService;

    /**
     * 获取用户地域分布
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "获取用户区域分布")
    @GetMapping("/admin/users/area")
    public Result<List<UserAreaDTO>> listUserAreas(ConditionVO conditionVO){
        return Result.ok(userAuthService.listUserAreas(conditionVO));
    }

    /**
     * 修改管理员的密码
     */
    @ApiOperation(value = "修改管理员的密码")
    @PutMapping("/admin/users/password")
    public Result<?> updateAdminPassword(@Valid @RequestBody PasswordVO passwordVO){
        userAuthService.updateAdminPassword(passwordVO);
        return Result.ok();
    }

    /**
     * 查询后台用户列表
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询后台用户列表")
    @GetMapping("/admin/users")
    public Result<PageResult<UserBackDTO>> listUsers(ConditionVO conditionVO){
        return Result.ok(userAuthService.listUserBackDTO(conditionVO));
    }


}
