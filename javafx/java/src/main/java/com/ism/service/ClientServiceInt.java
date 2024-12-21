package com.ism.service;

import com.ism.core.Database.ClientRepoListInt;
import com.ism.core.Database.Service;
import com.ism.entities.Client;
import com.ism.entities.User;

public interface ClientServiceInt extends Service<Client,ClientRepoListInt> {
  Client searchClient(String phone);
  Client searchSurname(String name);
  Client searchUser(User user);
}
