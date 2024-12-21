package com.ism.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"demande","article"})
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "demandeArticle")

public class DemandeArticle extends  AbstractEntity {
  private Double somme;
  private int qteDemande;
  @ManyToOne
  @JoinColumn(nullable = false)
  private Demande demande;
  @ManyToOne
  @JoinColumn(nullable = false)
  private Article article;
  
}
