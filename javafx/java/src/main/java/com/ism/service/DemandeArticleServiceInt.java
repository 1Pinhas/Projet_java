package com.ism.service;

import com.ism.core.Database.DemandeArticleRepoListInt;
import com.ism.core.Database.Service;
import com.ism.entities.Article;
import com.ism.entities.DemandeArticle;

public interface DemandeArticleServiceInt extends Service<DemandeArticle, DemandeArticleRepoListInt>{
  DemandeArticle verf(Article article);
}
