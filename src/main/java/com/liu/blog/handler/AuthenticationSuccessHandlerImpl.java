package com.liu.blog.handler;

import com.alibaba.fastjson.JSON;
import com.liu.blog.dao.UserAuthDao;
import com.liu.blog.dto.UserInfoDTO;
import com.liu.blog.entity.UserAuth;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.util.UserUtils;
import com.liu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.liu.blog.constant.CommonConst.APPLICATION_JSON;


/**
 * 登录成功处理
 *
 * @author yezhiqiu
 * @date 2021/07/28
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Autowired
    private UserAuthDao userAuthDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // 返回登录信息
        UserInfoDTO userLoginDTO = BeanCopyUtils.copyObject(UserUtils.getLoginUser(), UserInfoDTO.class);
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.ok(userLoginDTO)));
        // 更新用户ip，最近登录时间
        updateUserInfo();
    }

    /**
     * 更新用户信息
     */
    @Async
    public void updateUserInfo() {
        UserAuth userAuth = UserAuth.builder()
                .id(UserUtils.getLoginUser().getId())
                .ipAddress(UserUtils.getLoginUser().getIpAddress())
                .ipSource(UserUtils.getLoginUser().getIpSource())
                .lastLoginTime(UserUtils.getLoginUser().getLastLoginTime())
                .build();
        userAuthDao.updateById(userAuth);
    }

}
