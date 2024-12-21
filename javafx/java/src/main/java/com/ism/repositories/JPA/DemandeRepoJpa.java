package com.ism.repositories.JPA;

import com.ism.core.Database.DemandeRepoListInt;
import com.ism.repositories.Impl.RepositoryJpaImpl;
import com.ism.entities.Demande;

public class DemandeRepoJpa extends RepositoryJpaImpl<Demande> implements DemandeRepoListInt{

  public DemandeRepoJpa() {
    super(Demande.class);
    table = "Demande";
  }
  
}
