package com.ism.repositories.JPA;

import com.ism.core.Database.DemandeArticleRepoListInt;
import com.ism.repositories.Impl.RepositoryJpaImpl;
import com.ism.entities.DemandeArticle;

public class DemandeArticleRepoJpa extends RepositoryJpaImpl<DemandeArticle> implements DemandeArticleRepoListInt{

  public DemandeArticleRepoJpa() {
    super(DemandeArticle.class);
    table = "DemandeArticle";
  }
  @Override
  public int insert(DemandeArticle amour) {
    try {
      em.getTransaction().begin();
      em.merge(amour);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
    }
    return 0;
  }
  
}
