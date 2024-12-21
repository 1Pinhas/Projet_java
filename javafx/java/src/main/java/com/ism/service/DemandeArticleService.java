package com.ism.service;

import java.util.List;

import com.ism.core.Database.DemandeArticleRepoListInt;
import com.ism.entities.Article;
import com.ism.entities.DemandeArticle;

public class DemandeArticleService implements DemandeArticleServiceInt {

  private DemandeArticleRepoListInt  demandeArticleRepo;




  public DemandeArticleService(DemandeArticleRepoListInt  demandeArticleRepo) {
    this.demandeArticleRepo = demandeArticleRepo;
  }

 @Override
  public boolean saveList(DemandeArticle objet) {
    if(objet != null){
      demandeArticleRepo.insert(objet);
      return true;
    }
    return false;
  }

  @Override
  public List<DemandeArticle> show() {
    return demandeArticleRepo.selectAll();
  }

  @Override
  public DemandeArticleRepoListInt findData() {
    return demandeArticleRepo;
  }

  @Override
  public DemandeArticle verf(Article article) {
    return article.getDemandeArticles().stream()
        .filter(demandeArticle -> article.equals(demandeArticle.getArticle()))
        .findFirst()
        .orElse(null);
  }

  
  
}
