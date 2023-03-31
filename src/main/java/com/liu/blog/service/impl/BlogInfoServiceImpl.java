package com.liu.blog.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.liu.blog.dao.*;
import com.liu.blog.dto.*;
import com.liu.blog.entity.Article;
import com.liu.blog.entity.WebsiteConfig;
import com.liu.blog.service.BlogInfoService;
import com.liu.blog.service.PageService;
import com.liu.blog.service.RedisService;
import com.liu.blog.service.UniqueViewService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.util.IpUtils;
import com.liu.blog.vo.BlogInfoVO;
import com.liu.blog.vo.PageVO;
import com.liu.blog.vo.WebsiteConfigVO;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;


import java.util.*;
import java.util.stream.Collectors;

import static com.liu.blog.constant.CommonConst.*;
import static com.liu.blog.constant.RedisPrefixConst.*;
import static com.liu.blog.enums.ArticleStatusEnum.PUBLIC;

@Service
public class BlogInfoServiceImpl implements BlogInfoService {
    @Autowired
    RedisService redisService;
    @Autowired
    private PageService pageService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private WebsiteConfigDao websiteConfigDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UniqueViewService uniqueViewService;

    @Override
    public BlogHomeInfoDTO getBlogHomeInfo() {
        //查询文章数量
        Integer articleCount = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, PUBLIC.getStatus())
                .eq(Article::getIsDelete, FALSE));
        //查询分类数量
        Integer categoryCount = categoryDao.selectCount(null);
        //查询标签数量
        Integer tagCount = tagDao.selectCount(null);
        //查询访问量
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        String viewsCount = Optional.ofNullable(count).orElse(0).toString();
        //查询网站配置
        WebsiteConfigVO websiteConfig = this.getWebsiteConfig();
        //查询页面图片
        List<PageVO> pageVOList = pageService.listPages();
        return BlogHomeInfoDTO.builder()
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .viewsCount(viewsCount)
                .websiteConfig(websiteConfig)
                .pageList(pageVOList)
                .build();
    }
    @Override
    public BlogBackInfoDTO getBlogBackInfo() {
        // 查询访问量
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        Integer viewsCount = Integer.parseInt(Optional.ofNullable(count).orElse(0).toString());
        // 查询留言量
        Integer messageCount = messageDao.selectCount(null);
        // 查询用户量
        Integer userCount = userInfoDao.selectCount(null);
        // 查询文章量
        Integer articleCount = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, FALSE));
        // 查询一周用户量
        List<UniqueViewDTO> uniqueViewList = uniqueViewService.listUniqueViews();
        // 查询文章统计
        List<ArticleStatisticsDTO> articleStatisticsList = articleDao.listArticleStatistics();
        // 查询分类数据
        List<CategoryDTO> categoryDTOList = categoryDao.listCategoryDTO();
        // 查询标签数据
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagDao.selectList(null), TagDTO.class);
        // 查询redis访问量前五的文章
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(ARTICLE_VIEWS_COUNT, 0, 4);
        BlogBackInfoDTO blogBackInfoDTO = BlogBackInfoDTO.builder()
                .articleStatisticsList(articleStatisticsList)
                .tagDTOList(tagDTOList)
                .viewsCount(viewsCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryDTOList(categoryDTOList)
                .uniqueViewDTOList(uniqueViewList)
                .build();
        if (CollectionUtils.isNotEmpty(articleMap)) {
            // 查询文章排行
            List<ArticleRankDTO> articleRankDTOList = listArticleRank(articleMap);
            blogBackInfoDTO.setArticleRankDTOList(articleRankDTOList);
        }
        return blogBackInfoDTO;
    }
    public WebsiteConfigVO getWebsiteConfig(){
        WebsiteConfigVO websiteConfigVO;
        //获取缓存数据
        Object websiteConfig = redisService.get(WEBSITE_CONFIG);
        if(Objects.nonNull(websiteConfig)){
            websiteConfigVO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVO.class);
        }else {
            //从数据库中加载
            String config = websiteConfigDao.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class);
            redisService.set(WEBSITE_CONFIG,config);
        }
        return websiteConfigVO;
    }

    @Override
    public void updateAbout(BlogInfoVO blogInfoVO) {
        redisService.set(ABOUT,blogInfoVO.getAboutContent());
    }

    @Override
    public String getAbout() {
        Object o = redisService.get(ABOUT);
        return Objects.nonNull(o) ? o.toString() : "";
    }

    @Override
    public void report() {
        //获取ip
        String ipAddress = IpUtils.getIdAddress(request);
        //获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        //生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        //判断用户是否访问
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            //统计游客地域分布
            String ipSource = IpUtils.getIpSource(ipAddress);
            if(StringUtils.isNotBlank(ipSource)){
                ipSource = ipSource.substring(0, 2)
                        .replaceAll(PROVINCE, "")
                        .replaceAll(CITY, "");
                redisService.hIncr(VISITOR_AREA,ipSource,1L);
            }else {
                redisService.hIncr(VISITOR_AREA,UNKNOWN,1L);
            }
            //访问量+1
            redisService.incr(BLOG_VIEWS_COUNT,1);
            //保存唯一标识
            redisService.sAdd(UNIQUE_VISITOR,md5);
        }
    }

    @Override
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .id(1)
                .config(JSON.toJSONString(websiteConfigVO))
                .build();
        //将数组存放到表中
        websiteConfigDao.updateById(websiteConfig);
        //删除缓存
        redisService.del(WEBSITE_CONFIG);
    }

    /**
     * 查询文章排行
     *
     * @param articleMap 文章信息
     * @return {@link List<ArticleRankDTO>} 文章排行
     */
    private List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
        // 提取文章id
        List<Integer> articleIdList = new ArrayList<>(articleMap.size());
        articleMap.forEach((key, value) -> articleIdList.add((Integer) key));
        // 查询文章信息
        return articleDao.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle)
                .in(Article::getId, articleIdList))
                .stream().map(article -> ArticleRankDTO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewsCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankDTO::getViewsCount).reversed())
                .collect(Collectors.toList());
    }

}
