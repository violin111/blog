package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.TagBackDTO;
import com.liu.blog.dto.TagDTO;
import com.liu.blog.entity.Tag;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.TagVO;

import java.util.List;

public interface TagService extends IService<Tag> {
    /**
     *搜索文章标签
     * @param conditionVO
     * @return
     */
    List<TagDTO> listTagsBySearch(ConditionVO conditionVO);

    /**
     * 查询后台标签
     *
     * @param condition 条件
     * @return {@link PageResult<TagBackDTO>} 标签列表
     */
    PageResult<TagBackDTO> listTagBackDTO(ConditionVO condition);

    /**
     * 新增或者修改标签
     * @param tagVO
     */
    void saveOrUpdateTag(TagVO tagVO);

    /**
     * 删除标签
     * @param tagIdList
     */
    void deleteTag(List<Integer> tagIdList);
}
