package com.ism.repositories.JPA;


import com.ism.core.Database.ClientRepoListInt;
import com.ism.repositories.Impl.RepositoryJpaImpl;
import com.ism.entities.Client;


public class ClientRepoJpa extends RepositoryJpaImpl<Client> implements ClientRepoListInt {


  public ClientRepoJpa() {
    super(Client.class);
    table = "Client";
}

  @Override
  public Client selectByPhone(String phone) {
    try {
      em.getTransaction().begin();
      data= this.em.createNamedQuery("SelectByPhone", Client.class)
      .setParameter("tel", phone)
      .getSingleResult();    
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
    }
    return data;
  }

  @Override
  public Client selectBySurname(String val) {
    try {
      em.getTransaction().begin();
      data= this.em.createNamedQuery("SelectBySurname", Client.class)
      .setParameter("name", val)
      .getSingleResult();    
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
    }
    return data;
  }

  

  

  


 
  
}
