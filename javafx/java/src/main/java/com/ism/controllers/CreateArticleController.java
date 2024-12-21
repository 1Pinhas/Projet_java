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

public class CreateArticleController extends CoreController {
  
@FXML
private TextField ref;
@FXML
private TextField libelle;
@FXML
private TextField prix;
@FXML
private TextField qte;

private ArticleRepoListInt  articleRepo;
private ArticleServiceInt  articleService;




public CreateArticleController() {
    articleRepo = new ArticleRepoJpa();
    articleService = new ArticleService(articleRepo);
}

@FXML
private void create() throws IOException {
  Article michel = new Article();
  if (qte.getText().isEmpty() || !qte.getText().matches("\\d+")) {
    showAlert("Erreur de connexion", "Entrez des entiers.");
  }
  if (prix.getText().isEmpty() || !prix.getText().matches("\\d+")) {
    showAlert("Erreur de connexion", "Entrez des entiers.");
  }
  Integer qteField = Integer.valueOf(qte.getText());
  String refField = ref.getText();
  String libelleField = libelle.getText();
  Double prixField = Double.valueOf(prix.getText());
  michel.setRef(refField);
  michel.setLibelle(libelleField);
  michel.setPrix(prixField);
  michel.setQteStock(qteField);
  articleService.saveList(michel);
  App.setRoot("listerArticle");


}






}
