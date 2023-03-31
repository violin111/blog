package com.liu.blog.controller;

import com.liu.blog.annotation.OptLog;
import com.liu.blog.dto.TagBackDTO;
import com.liu.blog.dto.TagDTO;
import com.liu.blog.service.TagService;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.Result;
import com.liu.blog.vo.TagVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.liu.blog.constant.OptTypeConst.REMOVE;

/**
 * 标签模块
 */
@Api(tags = "标签模块")
@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 搜索文章标签
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "搜索文章标签")
    @GetMapping("/admin/tags/search")
    public Result<List<TagDTO>> listTagsBySearch(ConditionVO conditionVO){
        return Result.ok(tagService.listTagsBySearch(conditionVO));
    }

    /**
     * 查询后台标签列表
     *
     * @param condition 条件
     * @return {@link Result<TagBackDTO>} 标签列表
     */
    @ApiOperation(value = "查询后台标签列表")
    @GetMapping("/admin/tags")
    public Result<PageResult<TagBackDTO>> listTagBackDTO(ConditionVO condition) {
        return Result.ok(tagService.listTagBackDTO(condition));
    }

    /**
     * 新增后台标签列表
     * @param tagVO
     * @return
     */
    @ApiOperation(value = "新增后台标签列表")
    @PostMapping("/admin/tags")
    public Result<?> saveOrUpdateTag(@Valid @RequestBody TagVO tagVO){
        tagService.saveOrUpdateTag(tagVO);
        return Result.ok();
    }

    /**
     * 删除标签
     * @param tagIdList
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除标签")
    @DeleteMapping("/admin/tags")
    public Result<?> deleteTag(@RequestBody List<Integer> tagIdList){
        tagService.deleteTag(tagIdList);
        return Result.ok();
    }
}
