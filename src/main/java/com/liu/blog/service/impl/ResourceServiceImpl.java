package com.liu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.dao.ResourceDao;
import com.liu.blog.dao.RoleResourceDao;
import com.liu.blog.dto.LabelOptionDTO;
import com.liu.blog.dto.ResourceDTO;
import com.liu.blog.entity.Resource;
import com.liu.blog.entity.RoleResource;
import com.liu.blog.exception.BizException;
import com.liu.blog.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.liu.blog.service.ResourceService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.liu.blog.constant.CommonConst.FALSE;

@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, Resource> implements ResourceService {
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private RoleResourceDao roleResourceDao;
    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @Override
    public List<LabelOptionDTO> listResourceOption() {
        // 查询资源列表
        List<Resource> resourceList = resourceDao.selectList(new LambdaQueryWrapper<Resource>()
                .select(Resource::getId, Resource::getResourceName, Resource::getParentId)
                .eq(Resource::getIsAnonymous, FALSE));
        // 获取所有模块
        List<Resource> parentList = listResourceModule(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resourceList);
        // 组装父子数据
        return parentList.stream().map(item -> {
            List<LabelOptionDTO> list = new ArrayList<>();
            List<Resource> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .map(resource -> LabelOptionDTO.builder()
                                .id(resource.getId())
                                .label(resource.getResourceName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getResourceName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<ResourceDTO> listResources(ConditionVO conditionVO) {
        //查看资源列表
        List<Resource> resourceList = resourceDao.selectList(new LambdaQueryWrapper<Resource>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Resource::getResourceName, conditionVO.getKeywords()));
        //获取所有模块
        List<Resource> parentList = listResourceModule(resourceList);
        //根据父id分组获取模块下的资源
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resourceList);
        List<ResourceDTO> resourceDTOList = parentList.stream().map(item -> {
            ResourceDTO resourceDTO = BeanCopyUtils.copyObject(item, ResourceDTO.class);
            List<ResourceDTO> childrenList = BeanCopyUtils.copyList(childrenMap.get(item.getId()), ResourceDTO.class);
            resourceDTO.setChildren(childrenList);
            childrenList.remove(item.getId());
            return resourceDTO;
        }).collect(Collectors.toList());
        // 若还有资源未取出则拼接
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Resource> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<ResourceDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtils.copyObject(item, ResourceDTO.class))
                    .collect(Collectors.toList());
            resourceDTOList.addAll(childrenDTOList);
        }
        return resourceDTOList;
    }

    @Override
    public void saveOrUpdateResource(ResourceVO resourceVO) {
        //保存或者更新资源
        this.saveOrUpdate(BeanCopyUtils.copyObject(resourceVO,Resource.class));
        //重新加载资源
        filterInvocationSecurityMetadataSource.clearDataSource();
    }

    @Override
    public void deleteResource(Integer resourceId) {
        //首先判断数据库中是否存在
        Integer count = roleResourceDao.selectCount(new LambdaQueryWrapper<RoleResource>()
                .eq(RoleResource::getId, resourceId));
        if(count>0){
            throw new BizException("该资源下存在角色");
        }
        // 删除子资源
        List<Integer> resourceIdList = resourceDao.selectList(new LambdaQueryWrapper<Resource>()
                .select(Resource::getId).
                        eq(Resource::getParentId, resourceId))
                .stream()
                .map(Resource::getId)
                .collect(Collectors.toList());
        resourceIdList.add(resourceId);
        resourceDao.deleteBatchIds(resourceIdList);
    }

    /**
     * 获取模块下面的所有资源
     * @param parentList
     * @return
     */
    private Map<Integer, List<Resource>> listResourceChildren(List<Resource> parentList){
        return parentList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Resource::getParentId));
    }
    /**
     * 获取所有资源的模块
     * @param resourceList
     * @return
     */
    private List<Resource> listResourceModule(List<Resource> resourceList){
        return resourceList.stream()
                .filter(item-> Objects.isNull(item.getParentId()))
                .collect(Collectors.toList());
    }
}
