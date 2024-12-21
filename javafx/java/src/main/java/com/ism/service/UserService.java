package com.ism.service;


import java.util.List;

import com.ism.core.Database.UserRepoListInt;
import com.ism.entities.User;
import com.ism.enums.Etat;

import lombok.Data;

@Data

public class UserService implements UserServiceInt{

  private UserRepoListInt userRepo;

  
  public UserService(UserRepoListInt userRepo) {
    this.userRepo = userRepo;
  }


  @Override
  public boolean saveList(User objet) {
    if(objet != null){
      userRepo.insert(objet);
      return true;
    }
    return false;
  }


  @Override
  public List<User> show() {
    return userRepo.selectAll();
  }




  @Override
  public void Off(User user) {
    user.setEtat(Etat.Desactiver);
  }


  @Override
  public void On(User amour) {
    amour.setEtat(Etat.Activer);
  }


  @Override
  public UserRepoListInt findData() {
    return userRepo;
  }


  


  


  
  
  
}
