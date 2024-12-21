package com.ism.repositories.JPA;

import com.ism.core.Database.ArticleRepoListInt;
import com.ism.repositories.Impl.RepositoryJpaImpl;
import com.ism.entities.Article;

public class ArticleRepoJpa extends RepositoryJpaImpl<Article> implements ArticleRepoListInt {

  public ArticleRepoJpa() {
    super(Article.class);
    table = "Article";
  }

  @Override
  public Article selectByLibelle(String val) {
    datas = selectAll();
    return datas.stream()
    .filter(article -> article.getLibelle().compareTo(val) == 0)
    .findFirst()
    .orElse(null);
  }
  
}
