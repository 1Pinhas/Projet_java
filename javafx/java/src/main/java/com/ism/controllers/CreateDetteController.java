package com.ism.controllers;

import java.io.IOException;
import java.util.List;

import com.ism.App;
import com.ism.core.Database.ArticleRepoListInt;
import com.ism.core.Database.ClientRepoListInt;
import com.ism.core.Database.DetteRepoListInt;
import com.ism.entities.Article;
import com.ism.entities.Client;
import com.ism.entities.Commande;
import com.ism.enums.EtatDette;
import com.ism.repositories.JPA.ArticleRepoJpa;
import com.ism.repositories.JPA.ClientRepoJpa;
import com.ism.repositories.JPA.DetteRepoJpa;
import com.ism.service.ArticleService;
import com.ism.service.ArticleServiceInt;
import com.ism.service.ClientService;
import com.ism.service.ClientServiceInt;
import com.ism.service.DetteServceInt;
import com.ism.service.DetteService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CreateDetteController extends CoreController {
   
    @FXML
    private ChoiceBox<String> choiceBoxArticle;
    @FXML
    private TextField qte;
    @FXML
    private TextField searchField;

    private ArticleRepoListInt  articleRepoList;
    private ArticleServiceInt  articleService;
    private ClientServiceInt clientService;
    private ClientRepoListInt clientRepo;
    private DetteRepoListInt  detteRepo;
    private DetteServceInt detteService;
    private Commande dette = new Commande();




    public CreateDetteController() {
      this.articleRepoList = new ArticleRepoJpa();
      this.articleService = new ArticleService(articleRepoList);
      clientRepo = new ClientRepoJpa();
      clientService = new ClientService(clientRepo);
      detteRepo = new DetteRepoJpa();
      detteService = new DetteService(detteRepo);
    }

    @FXML
    public void initialize() {
        loadArticles();
    }
    

    // public void restrictInputToInteger(TextField qte) {
    //     qte.textProperty().addListener((observable, oldValue, newValue) -> {
    //         if (!newValue.matches("\\d*")) { // Expression régulière pour les chiffres seulement
    //             qte.setText(newValue.replaceAll("[^\\d]", "")); // Remplace les caractères non numériques
    //         }
    //     });
    // }

    private void loadArticles() {
        // Récupérer la liste des articles depuis le service
        List<Article> articles = articleService.show();
        
        // Extraire les libellés des articles
        ObservableList<String> articleLibelles = FXCollections.observableArrayList();
        for (Article article2 : articles) {
            articleLibelles.add(article2.getLibelle());
        }

        // Ajouter les libellés au ChoiceBox
        choiceBoxArticle.setItems(articleLibelles);
    }
  





    // @FXML
    // private void create() throws IOException {
    //     Detail detail = add();
    //     if (detail != null) {
    //         detailService.saveList(detail);
    //         detteService.saveList(dette);
    //         App.setRoot("listerClient");
    //     }

    // }

    // private Detail add() {
    //     // dette.setDetails(new ArrayList<>());
    //     if (qte.getText().isEmpty() || !qte.getText().matches("\\d+")) {
    //         showAlert("Erreur de connexion", "Entrez des entiers.");
    //     }
    //     Integer qteField = Integer.valueOf(qte.getText());
    //     String  articleBox = choiceBoxArticle.getSelectionModel().getSelectedItem();
    //     String numero= searchField.getText();
    //     Article article = articleService.findData().selectByLibelle(articleBox);
    //     if (article == null || qteField > article.getQteStock()) {
    //       showAlert("Erreur de connexion", "L'article n'a pas été trouver ou la quantité est superieure à celui de l'article.");
    //     }else{
    //         Client client = clientService.findData().selectByPhone(numero);
    //         if (client == null) {
    //           showAlert("Erreur de connexion", "le client n'existe pas.");
    //         }
    //         Detail detail2 = detailService.verf(article);
    //         article.setQteStock(article.getQteStock() -  qteField);
    //         articleService.findData().updateAllInt(article.getId(), "qteStock", article.getQteStock());
    //         if (detail2!= null) {
    //           detail2.setQteVendu(detail2.getQteVendu() + qteField);
    //           detail2.setSomme(detail2.getQteVendu() * article.getPrix());
    //           detailService.findData().update(detail2);
    //           System.out.println("LLKIJIJ");
    //         }else{
    //             dette.setMontant(0.0);
    //             Detail detail = new Detail();
    //             // article.setDetails(new ArrayList<>());
    //             detail.setArticle(article);
    //             detail.setQteVendu(detail.getQteVendu() + qteField);
    //             detail.setSomme(detail.getQteVendu() * article.getPrix());
    //             article.getDetails().add(detail);
    //             dette.setMontant(dette.getMontant() + detail.getSomme());
    //             dette.setMontantVerser(0.0);
    //             dette.setClient(client);
    //             dette.getDetails().add(detail);
    //             dette.setEtat(EtatDette.Nonarchiver);
    //             detail.setDette(dette);
    //             System.out.println(detail);
    //             System.out.println(dette.getDetails());
    //             return detail;
    //         }
    //     }
    //     return null;
    // }

    // @FXML
    // private void addArticle(){
    //     if (add() != null) {
    //         showAlert("Add", "L'article a été ajouter .");
    //     }
    //     else{
    //         showAlert("Erreur", "😒😒.");
    //     }
        
    // }



}
