package com.liu.blog.controller;

import com.liu.blog.annotation.OptLog;
import com.liu.blog.dto.RoleDTO;
import com.liu.blog.dto.UserRoleDTO;
import com.liu.blog.service.RoleService;
import com.liu.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.liu.blog.constant.OptTypeConst.REMOVE;
import static com.liu.blog.constant.OptTypeConst.SAVE_OR_UPDATE;

@Api(tags = "角色模块")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     *查询角色列表
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询角色列表")
    @GetMapping("/admin/roles")
    public Result<PageResult<RoleDTO>> listRoles(ConditionVO conditionVO){
        return Result.ok(roleService.listRoles(conditionVO));
    }

    /**
     * 保存或更新角色
     * @param roleVO
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新角色")
    @PostMapping("/admin/role")
    public Result<?> saveOrUpdateRole(@RequestBody @Valid RoleVO roleVO){
        roleService.saveOrUpdateRole(roleVO);
        return Result.ok();
    }

    /**
     * 删除角色
     * @param roleIdList
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/admin/roles")
    public Result<?> deleteRoles(@RequestBody List<Integer> roleIdList){
        roleService.deleteRoles(roleIdList);
        return Result.ok();
    }

    /**
     *
     * @return
     */
    @ApiOperation(value = "查看用户角色")
    @GetMapping("/admin/users/role")
    public Result<List<UserRoleDTO>> listUserRoles(){
        return Result.ok(roleService.listUserRoles());
    }
}
