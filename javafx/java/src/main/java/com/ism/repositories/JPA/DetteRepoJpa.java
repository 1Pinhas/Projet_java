package com.ism.repositories.JPA;


import com.ism.core.Database.DetteRepoListInt;
import com.ism.repositories.Impl.RepositoryJpaImpl;
import com.ism.entities.Commande;

public class DetteRepoJpa extends RepositoryJpaImpl<Commande> implements DetteRepoListInt{

  public DetteRepoJpa() {
    super(Commande.class);
    table = "Dette";
  }

  
  
}
