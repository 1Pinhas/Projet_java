package com.ism.core.Database;

import com.ism.repositories.Repository;
import com.ism.entities.User;

public interface UserRepoListInt extends Repository<User> {
  String login(String val);
  User selectForUser(String val,String val1,String val2);
  User  selectByLogin(String val);

}
