package com.ism.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "details")
@EqualsAndHashCode(callSuper = false , of = {"ref", "libelle"}) 
@Entity
@Table(name = "article")

public class Article extends AbstractEntity {
  
private String ref;
@Column(length = 25,unique = true)
private String libelle;
private Double prix;
private int qteStock;






@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
private List<DemandeArticle> demandeArticles = new ArrayList<>();
  
}
