package com.ism.core.Database;

import java.util.List;

public interface Views <T,A>{
  T created(A michel);
  void affiche(List<T> datas);
  
}
