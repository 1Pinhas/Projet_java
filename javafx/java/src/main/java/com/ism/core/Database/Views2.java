package com.ism.core.Database;

import java.util.List;

public interface Views2<T>{
  T created();
  void affiche(List<T> datas);
  
}
