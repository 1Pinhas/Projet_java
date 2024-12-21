package com.ism.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false , of = {"tel"}) 
@Entity
@Table(name = "client")
@NamedQueries({
  @NamedQuery(name ="SelectByPhone", query = "SELECT e FROM Client e WHERE e.tel = :tel"),
  @NamedQuery(name ="SelectBySurname", query = "SELECT e FROM Client e WHERE e.name = :name")
})
@ToString(exclude = {"user", "dettes","demandes"})
public class Client extends AbstractEntity {

    @Column(length = 25,unique = true)
    private String name;
    @Column(length = 25,unique = true)
    private String tel;
    @Column(length = 25,unique = false)
    private String adresse;

    

    //Relation avec Dette
    // @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Commande> dettes = new ArrayList<>();
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Demande> demandes = new ArrayList<>();

}
