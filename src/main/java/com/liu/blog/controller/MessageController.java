package com.liu.blog.controller;

import com.liu.blog.annotation.OptLog;
import com.liu.blog.dto.MessageBackDTO;
import com.liu.blog.service.MessageService;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.Result;
import com.liu.blog.vo.ReviewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.liu.blog.constant.OptTypeConst.REMOVE;
import static com.liu.blog.constant.OptTypeConst.UPDATE;

@Api(value = "留言管理")
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 查询后台留言列表
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查看后台留言列表")
    @GetMapping("/admin/messages")
    public Result<PageResult<MessageBackDTO>> listMessageBackDTO(ConditionVO conditionVO){
        return Result.ok(messageService.listMessageBackDTO(conditionVO));
    }
    /**
     * 删除留言
     *
     * @param messageIdList 留言id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除留言")
    @DeleteMapping("/admin/messages")
    public Result<?> deleteMessages(@RequestBody List<Integer> messageIdList){
        messageService.removeByIds(messageIdList);
        return Result.ok();
    }
    /**
     * 审核留言
     * @param reviewVO
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "审核留言")
    @PutMapping("/admin/messages/review")
    public Result<?> updateMessagesReview(@Valid @RequestBody ReviewVO reviewVO){
        messageService.updateMessagesReview(reviewVO);
        return Result.ok();
    }
}
