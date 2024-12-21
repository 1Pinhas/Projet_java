package com.ism.core.Database;

import java.util.List;


public interface Service<T,A> {
  boolean saveList (T objet);
  List<T> show();
  A findData();
}
