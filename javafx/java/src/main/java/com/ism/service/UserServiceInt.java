package com.ism.service;


import com.ism.core.Database.Service;
import com.ism.core.Database.UserRepoListInt;
import com.ism.entities.User;


public interface UserServiceInt extends Service<User,UserRepoListInt> {

  void Off (User amour);
  void On (User amour);
}
