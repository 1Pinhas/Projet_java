package com.ism.service;

import java.util.List;

import com.ism.core.Database.PaiementRepoListInt;
import com.ism.entities.Paiement;

public class PaiementService implements PaiementServiceInt {

  private PaiementRepoListInt  paiementRepo;



  public PaiementService(PaiementRepoListInt paiementRepo) {
    this.paiementRepo = paiementRepo;
  }

  @Override
  public boolean saveList(Paiement objet) {
    if(objet != null){
      paiementRepo.insert(objet);
      return true;
    }
    return false;
  }

  @Override
  public List<Paiement> show() {
    return paiementRepo.selectAll();
  }

  @Override
  public PaiementRepoListInt findData() {
    return paiementRepo;
  }
  
}
