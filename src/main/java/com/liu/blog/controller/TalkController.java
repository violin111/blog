package com.liu.blog.controller;


import com.liu.blog.dto.TalkBackDTO;
import com.liu.blog.service.TalkService;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.Result;
import com.liu.blog.vo.TalkVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "说说管理模块")
@RestController
public class TalkController {
    @Autowired
    private TalkService talkService;

    @GetMapping("/home/talks")
    public Result<List<String>> listHomeTalks(){
        return Result.ok(talkService.listHomeTalks());
    }

    /**
     * 保存或修改说说
     *
     * @param talkVO 说说信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "保存或修改说说")
    @PostMapping("/admin/talks")
    public Result<?> saveOrUpdateTalk(@Valid @RequestBody TalkVO talkVO) {
        talkService.saveOrUpdateTalk(talkVO);
        return Result.ok();
    }

    /**
     * 获取到说说信息
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查看后台说说")
    @GetMapping("/admin/talks")
    public Result<PageResult<TalkBackDTO>> listBackTalks(ConditionVO conditionVO){
        return Result.ok( talkService.listBackTalks(conditionVO));
    }

    /**
     * 删除说说
     *
     * @param talkIdList 说说id列表
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除说说")
    @DeleteMapping("/admin/talks")
    public Result<?> deleteTalks(@RequestBody List<Integer> talkIdList) {
        talkService.deleteTalks(talkIdList);
        return Result.ok();
    }
}
