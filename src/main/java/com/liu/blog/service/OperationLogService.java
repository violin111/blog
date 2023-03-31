package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.OperationLogDTO;
import com.liu.blog.entity.OperationLog;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;

public interface OperationLogService extends IService<OperationLog> {
    /**
     * 获取到操作日志
     * @param conditionVO
     * @return
     */
     PageResult<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);
}
