package com.liu.blog.controller;

import com.liu.blog.dto.FriendLinkBackDTO;
import com.liu.blog.dto.FriendLinkDTO;
import com.liu.blog.service.FriendLinkService;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "友链模块")
@RestController
public class FriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;
    /**
     * 查看后台友链列表
     *
     * @param condition 条件
     * @return {@link Result<FriendLinkBackDTO>} 后台友链列表
     */
    @ApiOperation(value = "查看后台友链列表")
    @GetMapping("/admin/links")
    public Result<PageResult<FriendLinkBackDTO>> listFriendLinkDTO(ConditionVO condition) {
        return Result.ok(friendLinkService.listFriendLinkDTO(condition));
    }

}
