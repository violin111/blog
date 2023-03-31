package com.liu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.constant.CommonConst;
import com.liu.blog.dao.RoleDao;
import com.liu.blog.dao.UserInfoDao;
import com.liu.blog.dao.UserRoleDao;
import com.liu.blog.dto.RoleDTO;
import com.liu.blog.dto.UserRoleDTO;
import com.liu.blog.entity.Role;
import com.liu.blog.entity.RoleMenu;
import com.liu.blog.entity.RoleResource;
import com.liu.blog.entity.UserRole;
import com.liu.blog.exception.BizException;
import com.liu.blog.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.liu.blog.service.RedisService;
import com.liu.blog.service.RoleMenuService;
import com.liu.blog.service.RoleResourceService;
import com.liu.blog.service.RoleService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.util.PageUtils;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.liu.blog.constant.CommonConst.FALSE;

/**
 * 角色服务
 *
 * @author yezhiqiu
 * @date 2021/07/28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;
    @Autowired
    private UserInfoDao userInfoDao;


    @Override
    public List<UserRoleDTO> listUserRoles() {
        //查询角色列表
        List<Role> roles = roleDao.selectList(new LambdaQueryWrapper<Role>()
                .select(Role::getId, Role::getRoleName));
        List<UserRoleDTO> userRoleDTOS = BeanCopyUtils.copyList(roles, UserRoleDTO.class);
        return userRoleDTOS;
    }

    @Override
    public PageResult<RoleDTO> listRoles(ConditionVO conditionVO) {
        //查询角色列表
        List<RoleDTO> roleDTOS = roleDao.listRoles(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        //查询出符合条件的数据数量
        Integer count = roleDao.selectCount(new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Role::getRoleName, conditionVO.getKeywords()));
        return new PageResult<>(roleDTOS,count);
    }

    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        //判断角色名是否重复
        Role existRole = roleDao.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getRoleName, roleVO.getRoleName()));
        if (Objects.nonNull(existRole)&& !(existRole.getId().equals(roleVO.getId()))){
            throw new BizException("角色名已存在");
        }
        //保存或更新用户信息
        Role role = Role.builder()
                .id(roleVO.getId())
                .roleName(roleVO.getRoleName())
                .roleLabel(roleVO.getRoleLabel())
                .isDisable(FALSE).build();
        this.saveOrUpdate(role);
        //更新角色资源关系
        if(Objects.nonNull(roleVO.getResourceIdList())){
            if(Objects.nonNull(role.getId())){
                roleResourceService.remove(new LambdaQueryWrapper<RoleResource>()
                        .eq(RoleResource::getId,roleVO.getId()));
            }
            List<RoleResource> collect = roleVO.getResourceIdList()
                    .stream()
                    .map(resourceId -> RoleResource.builder()
                            .roleId(roleVO.getId())
                            .resourceId(resourceId)
                            .build())
                    .collect(Collectors.toList());
            roleResourceService.saveBatch(collect);
            //重新加载角色资源信息
            filterInvocationSecurityMetadataSource.clearDataSource();
        }
        // 更新角色菜单关系
        if (Objects.nonNull(roleVO.getMenuIdList())) {
            if (Objects.nonNull(roleVO.getId())) {
                roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleVO.getId()));
            }
            List<RoleMenu> roleMenuList = roleVO.getMenuIdList().stream()
                    .map(menuId -> RoleMenu.builder()
                            .roleId(role.getId())
                            .menuId(menuId)
                            .build())
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
    }

    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        //首先验证该角色
        Integer count = userRoleDao.selectCount(new LambdaQueryWrapper<UserRole>()
                .in(UserRole::getId, roleIdList));
        if (count>0){
            throw new BizException("该角色下面存在用户");
        }
         roleDao.deleteBatchIds(roleIdList);
    }
}
