package com.ism.entities;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private LocalDate dateCreate;
  private LocalDate dateUpdate;




  @PrePersist
  public void PrePersist(){
    this.dateCreate = LocalDate.now();
    this.dateUpdate = LocalDate.now();
  }

  @PreUpdate
  public void PreUpdate(){
    this.dateUpdate = LocalDate.now();
  }



}
