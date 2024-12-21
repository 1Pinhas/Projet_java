// package com.ism.service;

// import java.util.List;

// import com.ism.core.Database.DetailRepoListInt;
// import com.ism.entities.Article;
// import com.ism.entities.Detail;

// public class DetailService implements DetailServiceInt {


//   private DetailRepoListInt detailRepo;


//   public DetailService(DetailRepoListInt detailRepo) {
//     this.detailRepo = detailRepo;
//   }

//   @Override
//   public boolean saveList(Detail objet) {
//     if(objet != null){
//       detailRepo.insert(objet);
//       return true;
//     }
//     return false;
//   }

//   @Override
//   public List<Detail> show() {
//     return detailRepo.selectAll();
//   }

//   @Override
//   public DetailRepoListInt findData() {
//     return detailRepo;
//   }

//   @Override
// public Detail verf(Article article) {
//     return article.getDetails().stream()
//         .filter(detail -> article.equals(detail.getArticle()))
//         .findFirst()
//         .orElse(null);
// }

// // @Override
// // public Detail getLastDetail(Dette dette) {
// //     List<Detail> details = dette.getDetails();
// //     if (details != null && !details.isEmpty()) {
// //         return details.get(details.size() - 1);
// //     }
// //     return null;
// // }


  
// }
