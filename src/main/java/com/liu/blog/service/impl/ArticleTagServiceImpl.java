package com.liu.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.dao.ArticleTagDao;
import com.liu.blog.entity.ArticleTag;
import com.liu.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTag> implements ArticleTagService {
}
