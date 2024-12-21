package com.ism.service;

import java.util.List;

import com.ism.core.Database.ClientRepoListInt;
import com.ism.entities.Client;
import com.ism.entities.User;

import lombok.Data;

@Data

public class ClientService implements ClientServiceInt {

  private ClientRepoListInt clientRepo;

  
  public ClientService(ClientRepoListInt clientRepo) {
    this.clientRepo = clientRepo;
  }


  @Override
  public boolean saveList(Client objet) {
    if(objet != null){
      clientRepo.insert(objet);
      return true;
    }
    return false;
  }


  @Override
  public List<Client> show() {
    return clientRepo.selectAll();
  }

  @Override
  public Client searchClient(String phone) {
    return clientRepo.selectByPhone(phone);
}


  @Override
  public ClientRepoListInt findData() {
    return clientRepo;
  }


  @Override
  public Client searchSurname(String name) {
    return clientRepo.selectBySurname(name);
  }


  @Override
public Client searchUser(User user) {
    return clientRepo.selectAll().stream()
            .filter(client -> client.getUser() != null && client.getUser().equals(user))
            .findFirst()
            .orElse(null);
}



  


  
  
}
