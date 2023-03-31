package com.liu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.dao.ArticleDao;
import com.liu.blog.dao.CategoryDao;
import com.liu.blog.dto.CategoryBackDTO;
import com.liu.blog.dto.CategoryDTO;
import com.liu.blog.dto.CategoryOptionDTO;
import com.liu.blog.entity.Article;
import com.liu.blog.entity.Category;
import com.liu.blog.exception.BizException;
import com.liu.blog.service.CategoryService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.util.PageUtils;
import com.liu.blog.vo.CategoryVO;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ArticleDao articleDao;
    @Override
    public PageResult<CategoryDTO> listCategories() {
        return new PageResult<>(categoryDao.listCategoryDTO(),categoryDao.selectCount(null));
    }

    @Override
    public PageResult<CategoryBackDTO> listBackCategories(ConditionVO conditionVO) {
        //查询分类数量
        Integer count = categoryDao.selectCount(new LambdaQueryWrapper<Category>()
                .eq(StringUtils.isNotBlank(conditionVO.getKeywords()), Category::getCategoryName, conditionVO.getKeywords()));
        if(count==0){
            return new PageResult<>();
        }
        //分页查询分类列表
        List<CategoryBackDTO> categoryBackDTOS = categoryDao.listCategoryBackDTO(PageUtils.getCurrent(), PageUtils.getSize(), conditionVO);
        return new PageResult<>(categoryBackDTOS,count);
    }

    @Override
    public List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO conditionVO) {
        List<Category> categories = categoryDao.selectList(new LambdaQueryWrapper<Category>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Category::getCategoryName, conditionVO.getKeywords())
                .orderByDesc(Category::getId));
        return BeanCopyUtils.copyList(categories,CategoryOptionDTO.class);
    }

    @Override
    public void deleteCategories(List<Integer> categoryIdList) {
        //查询分类id下是否有文章
        Integer count = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .in(Article::getCategoryId, categoryIdList));
        if(count>0){
            throw new BizException("删除失败，该分类下存在文章");
        }
        categoryDao.deleteBatchIds(categoryIdList);
    }

    @Override
    public void saveOrUpdateCategories(CategoryVO categoryVO) {
        //判断分类名重复
        Category category = categoryDao.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, categoryVO.getCategoryName()));
        if(Objects.nonNull(category)&&!category.getId().equals(categoryVO.getId())){
            throw new BizException("分类名已存在");
        }
        Category build = Category.builder().id(categoryVO.getId()).categoryName(categoryVO.getCategoryName()).build();
        this.saveOrUpdate(build);
    }
}
