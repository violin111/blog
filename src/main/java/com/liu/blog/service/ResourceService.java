package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.LabelOptionDTO;
import com.liu.blog.dto.ResourceDTO;
import com.liu.blog.entity.Resource;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.ResourceVO;

import java.util.List;

public interface ResourceService extends IService<Resource> {
    /**
     * 查看资源选项
     * @return
     */
    List<LabelOptionDTO> listResourceOption();

    /**
     *查看资源列表
     * @param conditionVO
     * @return
     */
    List<ResourceDTO> listResources(ConditionVO conditionVO);

    /**
     * 保存或者修改资源
     * @param resourceVO
     */
    void saveOrUpdateResource(ResourceVO resourceVO);

    /**
     * 根据id删除对应的资源
     * @param resourceId
     */
    void deleteResource(Integer resourceId);
}
