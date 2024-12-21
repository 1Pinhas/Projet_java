package com.ism.core.ServiceInstance.Impl;


import com.ism.core.Database.ArticleRepoListInt;
import com.ism.core.Database.ClientRepoListInt;
import com.ism.core.Database.Database;
import com.ism.core.Database.DemandeArticleRepoListInt;
import com.ism.core.Database.DemandeRepoListInt;
import com.ism.core.Database.DetteRepoListInt;
import com.ism.core.Database.PaiementRepoListInt;
import com.ism.core.Database.UserRepoListInt;
import com.ism.core.ServiceInstance.YamlServiceIns;

public class YamlServiceInsImpl implements YamlServiceIns{

    

  
     

  @Override
  public Object getInstanceClient(String repoType, String repoType2, ClientRepoListInt clientRepo) {
    try {
      String className = Database.getActiveDatabase(repoType, repoType2);
      if (className != null) {
          Class<?> clazz = Class.forName(className);
          return clazz.getDeclaredConstructor(ClientRepoListInt.class).newInstance(clientRepo);
      }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }

  @Override
  public Object getInstanceUser(String repoType, String repoType2, UserRepoListInt userRepo) {
    try {
      String className = Database.getActiveDatabase(repoType, repoType2);
      if (className != null) {
          Class<?> clazz = Class.forName(className);
          return clazz.getDeclaredConstructor(UserRepoListInt.class).newInstance(userRepo);
      }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }

  @Override
  public Object getInstanceArticle(String repoType, String repoType2, ArticleRepoListInt articleRepo) {
    try {
      String className = Database.getActiveDatabase(repoType, repoType2);
      if (className != null) {
          Class<?> clazz = Class.forName(className);
          return clazz.getDeclaredConstructor(ArticleRepoListInt.class).newInstance(articleRepo);
      }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }

  @Override
  public Object getInstanceDemande(String repoType, String repoType2, DemandeRepoListInt demandeRepo) {
    try {
      String className = Database.getActiveDatabase(repoType, repoType2);
      if (className != null) {
          Class<?> clazz = Class.forName(className);
          return clazz.getDeclaredConstructor(DemandeRepoListInt.class).newInstance(demandeRepo);
      }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }

  @Override
  public Object getInstanceDette(String repoType, String repoType2, DetteRepoListInt detteRepo) {
    try {
      String className = Database.getActiveDatabase(repoType, repoType2);
      if (className != null) {
          Class<?> clazz = Class.forName(className);
          return clazz.getDeclaredConstructor(DetteRepoListInt.class).newInstance(detteRepo);
      }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }

  @Override
  public Object getInstancePaiement(String repoType, String repoType2, PaiementRepoListInt paiementRepo) {
    try {
      String className = Database.getActiveDatabase(repoType, repoType2);
      if (className != null) {
          Class<?> clazz = Class.forName(className);
          return clazz.getDeclaredConstructor(PaiementRepoListInt.class).newInstance(paiementRepo);
      }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }

@Override
public Object getInstanceDemandeArticle(String repoType, String repoType2, DemandeArticleRepoListInt demandeRepo) {
    try {
        String className = Database.getActiveDatabase(repoType, repoType2);
        if (className != null) {
            Class<?> clazz = Class.forName(className);
            return clazz.getDeclaredConstructor(DemandeArticleRepoListInt.class).newInstance(demandeRepo);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
  
}
