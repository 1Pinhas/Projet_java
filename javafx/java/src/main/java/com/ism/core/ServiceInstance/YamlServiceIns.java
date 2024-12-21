package com.ism.core.ServiceInstance;

import com.ism.core.Database.ArticleRepoListInt;
import com.ism.core.Database.ClientRepoListInt;
import com.ism.core.Database.DemandeArticleRepoListInt;
import com.ism.core.Database.DemandeRepoListInt;
import com.ism.core.Database.DetteRepoListInt;
import com.ism.core.Database.PaiementRepoListInt;
import com.ism.core.Database.UserRepoListInt;

public interface  YamlServiceIns {
  Object getInstanceClient(String repoType, String repoType2,ClientRepoListInt clientRepo);
  Object getInstanceUser(String repoType, String repoType2,UserRepoListInt userRepo);
  Object getInstanceArticle(String repoType, String repoType2,ArticleRepoListInt articleRepo);
  Object getInstanceDemande(String repoType, String repoType2,DemandeRepoListInt demandeRepo);
  Object getInstanceDemandeArticle(String repoType, String repoType2,DemandeArticleRepoListInt demandeRepo);
  Object getInstanceDette(String repoType, String repoType2,DetteRepoListInt  detteRepo);
  Object getInstancePaiement(String repoType, String repoType2,PaiementRepoListInt  paiementRepo);
}