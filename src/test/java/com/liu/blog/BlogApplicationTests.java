package com.liu.blog;


import com.liu.blog.dao.PhotoAlbumDao;
import com.liu.blog.dao.UserAuthDao;
import com.liu.blog.dto.ArticleDTO;
import com.liu.blog.dto.PhotoAlbumBackDTO;
import com.liu.blog.entity.Article;
import com.liu.blog.entity.Talk;
import com.liu.blog.service.ArticleService;
import com.liu.blog.service.TalkService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.util.PageUtils;
import com.liu.blog.util.UserUtils;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.TalkVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class BlogApplicationTests {
    @Autowired
    ArticleService articleService;
    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private TalkService talkService;
    @Autowired
    private PhotoAlbumDao photoAlbumDao;


    @Test
    public void getArticleById(){
        ArticleDTO article=articleService.getArticleById(54);
        System.out.println(article);

    }

    @Test
    public  void  test(){
        ConditionVO conditionVO = new ConditionVO();
        conditionVO.setKeywords("");
        List<PhotoAlbumBackDTO> photoAlbumBackDTOS = photoAlbumDao.listPhotoAlbumBacks(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        for (int i = 0; i < photoAlbumBackDTOS.size(); i++) {
            System.out.println(photoAlbumBackDTOS.get(i).getAlbumName());
        }
    }

}
