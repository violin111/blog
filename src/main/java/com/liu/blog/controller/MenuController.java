package com.liu.blog.controller;


import com.liu.blog.dto.LabelOptionDTO;
import com.liu.blog.dto.MenuDTO;
import com.liu.blog.dto.UserMenuDTO;
import com.liu.blog.service.MenuService;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.MenuVO;
import com.liu.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "菜单模块")
@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;
    /**
     * 查询菜单列表
     *
     * @param conditionVO 条件
     * @return {@link Result<MenuDTO>} 菜单列表
     */
    @ApiOperation(value = "查.看菜单列表")
    @GetMapping("/admin/menus")
    public Result<List<MenuDTO>> listMenus(ConditionVO conditionVO) {
        return Result.ok(menuService.listMenus(conditionVO));
    }
    /**
     * 新增或修改菜单
     *
     * @param menuVO 菜单
     * @return {@link Result<>}
     */
    @ApiOperation(value = "新增或修改菜单")
    @PostMapping("/admin/menus")
    public Result<?> saveOrUpdateMenu(@Valid @RequestBody MenuVO menuVO) {
        menuService.saveOrUpdateMenu(menuVO);
        return Result.ok();
    }
    @DeleteMapping("/admin/menus/{menuId}")
    public Result<?> deleteMenu(@PathVariable("menuId") Integer menuId){
        menuService.deleteMenu(menuId);
        return Result.ok();
    }
    /**
     *查看角色菜单选项
     * @return
     */
    @ApiOperation(value = "查看角色菜单选项")
    @GetMapping("/admin/role/menus")
    public Result<List<LabelOptionDTO>> listMenuOptions(){
        return Result.ok(menuService.listMenuOptions());
    }

    /**
     * 查看当前用户菜单
     *
     * @return {@link Result<UserMenuDTO>} 菜单列表
     */
    @ApiOperation(value = "查看当前用户菜单")
    @GetMapping("/admin/user/menus")
    public Result<List<UserMenuDTO>> listUserMenus(){
        return Result.ok(menuService.listUserMenus());
    }


}
