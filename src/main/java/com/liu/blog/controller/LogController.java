package com.liu.blog.controller;

import com.liu.blog.dao.OperationLogDao;
import com.liu.blog.dto.OperationLogDTO;
import com.liu.blog.service.OperationLogService;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "操作日志页面")
@RestController
public class LogController {
    @Autowired
    private OperationLogService operationLogService;


    /**
     * 查看操作日志
     *
     * @param conditionVO 条件
     * @return {@link Result<OperationLogDTO>} 日志列表
     */
    @ApiOperation(value = "查看操作日志")
    @GetMapping("/admin/operation/logs")
    public Result<PageResult<OperationLogDTO>> listOperationLogs(ConditionVO conditionVO) {
        return Result.ok(operationLogService.listOperationLogs(conditionVO));
    }
}
