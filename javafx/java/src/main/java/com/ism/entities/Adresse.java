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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "adresse")
// @NamedQueries({
//   @NamedQuery(name ="SelectByPhone", query = "SELECT e FROM Client e WHERE e.tel = :tel"),
//   @NamedQuery(name ="SelectBySurname", query = "SELECT e FROM Client e WHERE e.name = :name")
// })
@ToString()
public class Adresse extends AbstractEntity {

    @Column(length = 25,unique = true)
    private String ville;
    @Column(length = 25,unique = true)
    private String quartier;
    @Column(length = 25,unique = false)
    private String numeroVilla;

    // Navigabilité vers User
    // @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // @JoinColumn
    // private User user;

    // //Relation avec Dette
    // // @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    // @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    // private List<Dette> dettes = new ArrayList<>();
    // @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    // private List<Demande> demandes = new ArrayList<>();

}
