package com.ism.core.Database;

import com.ism.repositories.Repository;
import com.ism.entities.Article;

public interface ArticleRepoListInt extends Repository<Article> {
  Article selectByLibelle(String articleLibelle);
}
