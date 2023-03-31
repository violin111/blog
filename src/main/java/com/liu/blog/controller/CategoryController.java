package com.liu.blog.controller;

import com.liu.blog.annotation.OptLog;
import com.liu.blog.dto.CategoryBackDTO;
import com.liu.blog.dto.CategoryOptionDTO;
import com.liu.blog.service.CategoryService;
import com.liu.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.liu.blog.constant.OptTypeConst.REMOVE;
import static com.liu.blog.constant.OptTypeConst.SAVE_OR_UPDATE;

@Api(value = "分类模块")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 搜索文章分类
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "搜索文章分类")
    @GetMapping("/admin/categories/search")
    public Result<List<CategoryOptionDTO>> listCategoriesBySearch(ConditionVO conditionVO){
        return Result.ok(categoryService.listCategoriesBySearch(conditionVO));
    }

    /**
     * 查询后台分类列表
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询后台分类列表")
    @GetMapping("/admin/categories")
    public Result<PageResult<CategoryBackDTO>> listBackCategories(ConditionVO conditionVO){
        return Result.ok(categoryService.listBackCategories(conditionVO));
    }

    /**
     * 删除分类
     * @param categoryIdList
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除分类")
    @DeleteMapping("/admin/categories")
    public Result<?> deleteCategories(@RequestBody List<Integer> categoryIdList){
        categoryService.deleteCategories(categoryIdList);
        return Result.ok();
    }

    /**
     * 保存或者修改分类
     * @param categoryVO
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或者修改分类")
    @PostMapping("/admin/categories")
    public Result<?> saveOrUpdateCategory(@Valid @RequestBody CategoryVO categoryVO){
        categoryService.saveOrUpdateCategories(categoryVO);
        return Result.ok();
    }
}
