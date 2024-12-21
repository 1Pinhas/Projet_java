package com.ism.repositories;

import java.util.List;


public interface Repository <A>{
  int insert(A amour);
  List<A> selectAll();
  void remove(int id);
  A selectById(int id);
  void updateAllString(int id, String val, String val1);
  void updateAllInt(int id, String val, int val1);
  void update(A objet);
}
