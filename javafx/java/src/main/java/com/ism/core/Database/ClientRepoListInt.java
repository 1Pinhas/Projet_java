package com.ism.core.Database;

import com.ism.repositories.Repository;
import com.ism.entities.Client;

public interface ClientRepoListInt extends Repository<Client> {
  Client selectByPhone(String phone);
  Client selectBySurname(String val);
}
