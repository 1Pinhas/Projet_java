package com.ism.service;

import com.ism.core.Database.DetteRepoListInt;
import com.ism.core.Database.Service;
import com.ism.entities.Commande;

public interface DetteServceInt extends Service<Commande, DetteRepoListInt> {
  void archiverSolider();
  Commande searchDette(int id);
}
