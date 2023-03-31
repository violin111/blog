package com.liu.blog.service;

import com.liu.blog.dto.BlogBackInfoDTO;
import com.liu.blog.dto.BlogHomeInfoDTO;
import com.liu.blog.vo.BlogInfoVO;
import com.liu.blog.vo.WebsiteConfigVO;

public interface BlogInfoService {
    /**
     * 获取首页数据
     *
     * @return 博客首页信息
     */
    BlogHomeInfoDTO getBlogHomeInfo();
    /**
     * 获取后台首页数据
     *
     * @return 博客后台信息
     */
    BlogBackInfoDTO getBlogBackInfo();
    /**
     * 上传访客信息
     */
    //返回访客的信息
    void report();

    /**
     * 修改网站的配置信息
     * @param websiteConfigVO
     */
    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    /**
     * 查看网站配置
     * @return
     */
    WebsiteConfigVO getWebsiteConfig();

    /**
     * 修改关于我内容
     *
     * @param blogInfoVO 博客信息
     */
    void updateAbout(BlogInfoVO blogInfoVO);

    /**
     * 获取关于我内容
     *
     * @return 关于我内容
     */
    String getAbout();
}
