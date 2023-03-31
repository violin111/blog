package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.LabelOptionDTO;
import com.liu.blog.dto.MenuDTO;
import com.liu.blog.dto.UserMenuDTO;
import com.liu.blog.entity.Menu;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.MenuVO;

import java.util.List;

public interface MenuService extends IService<Menu> {
    /**
     * 查看菜单列表
     *
     * @param conditionVO 条件
     * @return 菜单列表
     */
    List<MenuDTO> listMenus(ConditionVO conditionVO);

    /**
     * 查看角色菜单选项
     * @return
     */
    List<LabelOptionDTO> listMenuOptions();
    /**
     * 查看用户菜单
     *
     * @return 菜单列表
     */
    List<UserMenuDTO> listUserMenus();

    /**
     * 保存或者更新菜单
     * @param menuVO
     */
    void saveOrUpdateMenu(MenuVO menuVO);

    /**
     * 根据Id删除菜单
     * @param menuId
     */
    void deleteMenu(Integer menuId);
}
