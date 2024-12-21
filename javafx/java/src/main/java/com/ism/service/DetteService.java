package com.ism.service;

import java.util.List;

import com.ism.core.Database.DetteRepoListInt;
import com.ism.entities.Commande;
import com.ism.enums.EtatDette;

public class DetteService implements DetteServceInt{

  private DetteRepoListInt detteRepo;
  

  public DetteService(DetteRepoListInt detteRepoJpa) {
    this.detteRepo = detteRepoJpa;
  }

  @Override
  public boolean saveList(Commande objet) {
    if(objet != null){
      detteRepo.insert(objet);
      return true;
    }
    return false;
  }

  @Override
  public List<Commande> show() {
    return detteRepo.selectAll();
  }

  @Override
  public DetteRepoListInt findData() {
    return detteRepo;
  }

  @Override
  public void archiverSolider() {
    detteRepo.selectAll().stream()
        .peek(dette -> dette.setMontantRestant(dette.getMontant() - dette.getMontantVerser()))
        .filter(dette -> dette.getMontantRestant() == 0)
        .forEach(dette -> dette.setEtat(EtatDette.Archiver));
  }

  @Override
  public Commande searchDette(int id) {
   return detteRepo.selectById(id);
  }
  
}
