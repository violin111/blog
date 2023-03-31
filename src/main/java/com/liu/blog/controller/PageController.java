package com.liu.blog.controller;

import com.liu.blog.dto.PageDTO;
import com.liu.blog.service.PageService;
import com.liu.blog.vo.PageVO;
import com.liu.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PageController {
    @Autowired
    private PageService pageService;

    /**
     * 获取页面列表
     *
     * @return {@link Result<PageDTO>}
     */
    @ApiOperation(value = "获取页面列表")
    @GetMapping("/admin/pages")
    public Result<List<PageVO>> listPages() {
        return Result.ok(pageService.listPages());
    }
}
