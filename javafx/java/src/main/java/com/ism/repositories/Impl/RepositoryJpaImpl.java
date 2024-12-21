package com.ism.repositories.Impl;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


import com.ism.core.Database.Database;
import com.ism.repositories.Repository;
import lombok.Data;

@Data

public class RepositoryJpaImpl<T> implements Repository<T> {

  protected String table;
  protected EntityManager em;
  protected Class<T> type;
  protected List<T> datas = new ArrayList<>();
  protected T data = null;



  EntityManagerFactory emf = Database.getEntityManagerFactory("databases","active_db");


  
  


  public RepositoryJpaImpl(Class<T> type) {
    this.type = type;
    if (em==null) {
        em=emf.createEntityManager();
      }
    }

  @Override
  public int insert(T amour) {
    try {
      em.getTransaction().begin();
      em.persist(amour);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
    }
    return 0;
  }

//   @Override
//    public List<T> selectAll() {
//         List<T> list=null;
//         try {
//             em.getTransaction().begin();
//             list= this.em.createQuery("SELECT e FROM " + table + " e", type).getResultList();
//             em.getTransaction().commit();
//         } catch (Exception e) {
//            em.getTransaction().rollback();
//            e.printStackTrace();
//         }
//        return list;
// }

@Override
public List<T> selectAll() {
     return this.em.createQuery("SELECT e FROM " + table + " e", type)
     .getResultList();
}

@Override
public void remove(int id) {
    this.em.createQuery("DELETE FROM " + table + " e WHERE e.id = :id")
           .setParameter("id", id)
           .executeUpdate();
}


//Bien le voir donc à faire plus tard
// @Override
// public void remove(T amour) {
//   try {
//     em.getTransaction().begin();
//     em.remove(selectById(amour.get));
//     em.getTransaction().commit();
//   } catch (Exception e) {
//     em.getTransaction().rollback();
//   }
// }

@Override
public T selectById(int id) {
  try {
    return em.find(type, id);
  } catch (Exception e) {
    return null;
  }
}

@Override
public void updateAllString(int id, String fieldName, String newValue) {
    try {
        em.getTransaction().begin();
        String jpql = "UPDATE " + table + " e SET e." + fieldName + " = :newValue WHERE e.id = :id";
        em.createQuery(jpql)
          .setParameter("newValue", newValue)
          .setParameter("id", id)
          .executeUpdate();

        em.getTransaction().commit();
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    }
}

  
@Override
public void updateAllInt(int id, String fieldName, int newValue) {
    try {
        em.getTransaction().begin();
        String jpql = "UPDATE " + table + " e SET e." + fieldName + " = :newValue WHERE e.id = :id";
        em.createQuery(jpql)
          .setParameter("newValue", newValue)
          .setParameter("id", id)
          .executeUpdate();

        em.getTransaction().commit();
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    }
}

@Override
public void update(T objet) {
  try {
      em.getTransaction().begin();
      // Utiliser merge pour synchroniser l'état de l'entité avec la base de données
      em.merge(objet);
      em.getTransaction().commit();
  } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
  }
}

}
