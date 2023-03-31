package com.liu.blog.strategy.context;

import com.liu.blog.enums.MarkdownTypeEnum;
import com.liu.blog.strategy.ArticleImportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ArticleImportStrategyContext {
    @Autowired
    private Map<String, ArticleImportStrategy> articleImportStrategyMap;

    public void importArticles(MultipartFile file,String type){
        ArticleImportStrategy importStrategy = articleImportStrategyMap.get(MarkdownTypeEnum.getMarkdownType(type));
        importStrategy.importArticles(file);
    }
}
