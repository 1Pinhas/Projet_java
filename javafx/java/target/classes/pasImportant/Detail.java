// package com.ism.entities;

// import javax.persistence.Entity;
// import javax.persistence.ManyToOne;
// import javax.persistence.Table;

// import lombok.EqualsAndHashCode;
// import lombok.Getter;
// import lombok.Setter;
// import lombok.ToString;

// @Getter
// @Setter
// @ToString(exclude = {"dette"})
// @EqualsAndHashCode(callSuper = false)
// @Entity
// @Table(name = "detail")
// public class Detail extends AbstractEntity {
//   private Double somme;
//   private int qteVendu;
//   @ManyToOne
//   private Dette dette;
//   @ManyToOne
//   private Article article;
  
// }
