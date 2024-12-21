package com.ism.service;


import com.ism.core.Database.ArticleRepoListInt;
import com.ism.core.Database.Service;
import com.ism.entities.Article;


public interface ArticleServiceInt extends Service<Article,ArticleRepoListInt> {
  void updateQteStock(int qteRe,String val);
}
