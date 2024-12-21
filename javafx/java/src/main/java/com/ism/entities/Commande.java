package com.ism.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ism.enums.EtatDette;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "commande")
public class Commande extends AbstractEntity {
  private Double montant;
  private Double montantVerser;
  @Transient
  private Double montantRestant;
  @Enumerated(EnumType.STRING)
  private EtatDette etat;

  //Navigabilité

  @ManyToOne
  @JoinColumn
  private Client client;
  @OneToOne
  @JoinColumn
  private Demande demande;
  

  
  
  

}
