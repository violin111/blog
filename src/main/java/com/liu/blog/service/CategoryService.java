package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.CategoryBackDTO;
import com.liu.blog.dto.CategoryDTO;
import com.liu.blog.dto.CategoryOptionDTO;
import com.liu.blog.entity.Article;
import com.liu.blog.entity.Category;
import com.liu.blog.vo.CategoryVO;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CategoryService extends IService<Category> {
    //查询列表分类
    PageResult<CategoryDTO> listCategories();

    /**
     * 根据条件查询后台文章分类
     */
    PageResult<CategoryBackDTO> listBackCategories(ConditionVO conditionVO);


    /**
     * 删除分类
     */
    void deleteCategories(List<Integer> categoryIdList);
    //添加或修改文章分类
    void saveOrUpdateCategories(CategoryVO categoryVO);
    /**
     * 搜索文章分类
     *
     * @param conditionVO 条件
     * @return {@link List<CategoryOptionDTO>} 分类列表
     */
    List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO conditionVO);

}
