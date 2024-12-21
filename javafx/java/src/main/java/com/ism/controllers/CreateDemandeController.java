package com.ism.controllers;

import java.io.IOException;
import java.util.List;

import com.ism.App;
import com.ism.core.Database.ArticleRepoListInt;
import com.ism.core.Database.ClientRepoListInt;
import com.ism.core.Database.DemandeArticleRepoListInt;
import com.ism.core.Database.DemandeRepoListInt;
import com.ism.entities.Article;
import com.ism.entities.Client;
import com.ism.entities.Demande;
import com.ism.entities.DemandeArticle;
import com.ism.enums.EtatDeDemande;
import com.ism.repositories.JPA.ArticleRepoJpa;
import com.ism.repositories.JPA.ClientRepoJpa;
import com.ism.repositories.JPA.DemandeArticleRepoJpa;
import com.ism.repositories.JPA.DemandeRepoJpa;
import com.ism.service.ArticleService;
import com.ism.service.ArticleServiceInt;
import com.ism.service.ClientService;
import com.ism.service.ClientServiceInt;
import com.ism.service.DemandeArticleService;
import com.ism.service.DemandeArticleServiceInt;
import com.ism.service.DemandeService;
import com.ism.service.DemandeServiceInt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CreateDemandeController extends CoreController {
   
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
    private DemandeArticleRepoListInt  demandeArticleRepo;
    private DemandeArticleServiceInt  demandeArticleService;
    private DemandeRepoListInt  demandeRepo;
    private DemandeServiceInt demandeService;
    private Demande demande = new Demande();




    public CreateDemandeController() {
      this.articleRepoList = new ArticleRepoJpa();
      this.articleService = new ArticleService(articleRepoList);
      clientRepo = new ClientRepoJpa();
      clientService = new ClientService(clientRepo);
      demandeArticleRepo = new DemandeArticleRepoJpa();
      demandeArticleService = new DemandeArticleService(demandeArticleRepo);
      demandeRepo = new DemandeRepoJpa();
      demandeService = new DemandeService(demandeRepo);
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
  





    @FXML
    private void create() throws IOException {
        if (add() != null) {
            for (DemandeArticle demandeArticle : demande.getDemandeArticles()) {
                demandeArticleService.saveList(demandeArticle);
            }
            App.setRoot("listeDemandeClient");
        }

    }

    @FXML
    private void addArticle() throws IOException {
        if (add() != null) {
            System.out.println("fff");
            System.out.println(demande);
            showAlert("Add", "L'article a été ajouter .");
            App.setRoot("listeDemandeClient");
        }
        else{
            showAlert("Erreur", "😒😒.");
            App.setRoot("listeDemandeClient");
        }
        
    }

    private DemandeArticle add() {
        if (qte.getText().isEmpty() || !qte.getText().matches("\\d+")) {
            showAlert("Erreur de connexion", "Entrez des entiers.");
        }
        Integer qteField = Integer.valueOf(qte.getText());
        String  articleBox = choiceBoxArticle.getSelectionModel().getSelectedItem();
        String numero= searchField.getText();
        Article article = articleService.findData().selectByLibelle(articleBox);
        if (article == null || qteField > article.getQteStock()) {
          showAlert("Erreur de connexion", "L'article n'a pas été trouver ou la quantité est superieure à celui de l'article.");
        }else{
            Client client = clientService.findData().selectByPhone(numero);
            if (client == null || client.getUser() == null) {
              showAlert("Erreur de connexion", "le client n'existe pas ou n'a pas de role.");
              return null;
            }
            DemandeArticle demandeArticle2 = demandeArticleService.verf(article);
            article.setQteStock(article.getQteStock() -  qteField);
            articleService.findData().updateAllInt(article.getId(), "qteStock", article.getQteStock());
            if (demandeArticle2!= null) {
                demandeArticle2.setQteDemande(demandeArticle2.getQteDemande() + qteField);
                demandeArticle2.setSomme(demandeArticle2.getQteDemande() * article.getPrix());
              demandeArticleService.findData().update(demandeArticle2);
              System.out.println("LLKIJIJ");
            }else{
                DemandeArticle demandeArticle = new DemandeArticle();
                demandeArticle.setArticle(article);
                demandeArticle.setQteDemande(demandeArticle.getQteDemande() + qteField);
                demandeArticle.setSomme(demandeArticle.getQteDemande() * article.getPrix());
                article.getDemandeArticles().add(demandeArticle);
                demande.setClient(client);
                System.out.println(demande.getClient());
                demande.getDemandeArticles().add(demandeArticle);
                demande.setEtatDeDemande(EtatDeDemande.Enc_cours);
                demandeArticle.setDemande(demande);
                demandeService.saveList(demande);
                System.out.println(demandeArticle);
                System.out.println(demande);
                return demandeArticle;
            }
        }
        return null;
    }

    



}
