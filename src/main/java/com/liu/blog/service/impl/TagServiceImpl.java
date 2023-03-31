package com.liu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.dao.ArticleTagDao;
import com.liu.blog.dao.TagDao;
import com.liu.blog.dto.TagBackDTO;
import com.liu.blog.dto.TagDTO;
import com.liu.blog.entity.ArticleTag;
import com.liu.blog.entity.Tag;
import com.liu.blog.exception.BizException;
import com.liu.blog.service.TagService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.util.PageUtils;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private ArticleTagDao articleTagDao;

    @Override
    public List<TagDTO> listTagsBySearch(ConditionVO conditionVO) {
        //搜索标签
        List<Tag> tags = tagDao.selectList(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Tag::getTagName, conditionVO.getKeywords())
                .orderByDesc(Tag::getId));
        return BeanCopyUtils.copyList(tags,TagDTO.class);
    }

    @Override
    public PageResult<TagBackDTO> listTagBackDTO(ConditionVO condition) {
        // 查询标签数量
        Integer count = tagDao.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Tag::getTagName, condition.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询标签列表
        List<TagBackDTO> tagList = tagDao.listTagBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(tagList, count);
    }

    @Override
    public void saveOrUpdateTag(TagVO tagVO) {
        Tag tag = tagDao.selectOne(new LambdaQueryWrapper<Tag>().select(Tag::getId).eq(Tag::getTagName, tagVO.getTagName()));
        if(Objects.nonNull(tag)&&!tag.getId().equals(tagVO.getId())){
            throw new BizException("标签名已经存在");
        }
        this.saveOrUpdate(BeanCopyUtils.copyObject(tagVO,Tag.class));
    }

    @Override
    public void deleteTag(List<Integer> tagIdList) {
        //查询标签下是否存在文章
        Integer count = articleTagDao.selectCount(new LambdaQueryWrapper<ArticleTag>().in(ArticleTag::getTagId, tagIdList));
        if(count>0){
            throw new BizException("删除失败，该标签下存在文章");
        }
        tagDao.deleteBatchIds(tagIdList);
    }
}
