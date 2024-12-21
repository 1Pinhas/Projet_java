package com.ism.controllers;

import java.io.IOException;

import com.ism.App;
import com.ism.core.Database.ArticleRepoListInt;
import com.ism.entities.Article;
import com.ism.repositories.JPA.ArticleRepoJpa;
import com.ism.service.ArticleService;
import com.ism.service.ArticleServiceInt;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UpdateArticle extends CoreController {
  
@FXML
private TextField qte;

private ArticleRepoListInt  articleRepo;
private ArticleServiceInt  articleService;




public UpdateArticle() {
    articleRepo = new ArticleRepoJpa();
    articleService = new ArticleService(articleRepo);
}


@FXML
private void update() throws IOException {
  Article article = ArticleController.selectedArticle;
    if (qte.getText().isEmpty() || !qte.getText().matches("\\d+")) {
      showAlert("Erreur de connexion", "Entrez des entiers.");
    }
    Integer qteField = Integer.valueOf(qte.getText());
    article.setQteStock(article.getQteStock() + qteField);
    articleService.findData().update(article);
    App.setRoot("listerArticle");


}












}
