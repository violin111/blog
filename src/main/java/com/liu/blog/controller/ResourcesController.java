package com.liu.blog.controller;

import com.liu.blog.annotation.OptLog;
import com.liu.blog.dto.LabelOptionDTO;
import com.liu.blog.dto.ResourceDTO;
import com.liu.blog.dto.ResourceRoleDTO;
import com.liu.blog.service.ResourceService;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.ResourceVO;
import com.liu.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "资源模块")
@RestController
public class ResourcesController {
    @Autowired
    private ResourceService resourceService;

    /**
     * 查看资源列表
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查看资源列表")
    @GetMapping("/admin/resources")
    public Result<List<ResourceDTO>> listResources(ConditionVO conditionVO){
        return Result.ok(resourceService.listResources(conditionVO));
    }

    /**
     *新增或者修改资源
     * @param resourceVO
     * @return
     */
    @ApiOperation(value = "新增或者修改资源")
    @PostMapping("/admin/resources")
    public Result<?> saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO){
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.ok();
    }
    @DeleteMapping("/admin/resources/{resourceId}")
    public Result<?> deleteResource(@PathVariable Integer resourceId ){
        resourceService.deleteResource(resourceId);
        return Result.ok();
    }
    /**
     * 查看资源选项
     * @return
     */
    @ApiOperation(value ="查看资源选项" )
    @GetMapping("/admin/role/resources")
    public Result<List<LabelOptionDTO>> listResourceOption(){
        return Result.ok(resourceService.listResourceOption());
    }
}
