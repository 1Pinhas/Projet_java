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

import com.ism.enums.EtatDeDemande;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString 
@Entity
@Table(name = "demande")

public class Demande extends AbstractEntity{
  @Enumerated(EnumType.STRING)
  private EtatDeDemande etatDeDemande;
  private Double montant;
  @ManyToOne
  @JoinColumn
  private Client client;
  @OneToOne
  @JoinColumn
  private Commande dette;
  @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL)
  private List<DemandeArticle> demandeArticles = new ArrayList<>();
}
