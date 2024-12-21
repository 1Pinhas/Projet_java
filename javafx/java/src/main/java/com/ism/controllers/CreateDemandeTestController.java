package com.ism.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

import com.ism.core.Database.ArticleRepoListInt;
import com.ism.core.Database.DemandeArticleRepoListInt;
import com.ism.core.Database.DemandeRepoListInt;
import com.ism.entities.Article;
import com.ism.entities.Demande;
import com.ism.entities.DemandeArticle;
import com.ism.enums.EtatDeDemande;
import com.ism.repositories.JPA.ArticleRepoJpa;
import com.ism.repositories.JPA.DemandeArticleRepoJpa;
import com.ism.repositories.JPA.DemandeRepoJpa;
import com.ism.service.ArticleService;
import com.ism.service.ArticleServiceInt;
import com.ism.service.DemandeArticleService;
import com.ism.service.DemandeArticleServiceInt;
import com.ism.service.DemandeService;
import com.ism.service.DemandeServiceInt;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreateDemandeTestController extends CoreController {

  
    @FXML
    private ChoiceBox<String> articleChoiceBox;
    @FXML
    private TextField quantityField;
    @FXML
    private TableView<DemandeArticle> lineItemsTable;
    @FXML
    private TableColumn<DemandeArticle, String> libelleColumn;
    @FXML
    private TableColumn<DemandeArticle, Double> prixColumn;
    @FXML
    private TableColumn<DemandeArticle, Integer> qteStockColumn;
    @FXML
    private TableColumn<DemandeArticle, String> refColumn1;
    @FXML
    private Label totalLabe;
    @FXML
    private Label client;

    private final ObservableList<DemandeArticle> lineItems = FXCollections.observableArrayList();

    private ArticleRepoListInt  articleRepoList;
    private ArticleServiceInt  articleService;
    private DemandeArticleRepoListInt  demandeArticleRepo;
    private DemandeArticleServiceInt  demandeArticleService;
    private DemandeRepoListInt  demandeRepo;
    private DemandeServiceInt demandeService;
    private Demande demande = new Demande();





    public CreateDemandeTestController() {
      this.articleRepoList = new ArticleRepoJpa();
      this.articleService = new ArticleService(articleRepoList);
      demandeArticleRepo = new DemandeArticleRepoJpa();
      demandeArticleService = new DemandeArticleService(demandeArticleRepo);
      demandeRepo = new DemandeRepoJpa();
      demandeService = new DemandeService(demandeRepo);
    }

    @FXML
    public void initialize() {
      libelleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArticle().getLibelle()));
      prixColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getArticle().getPrix()));
      qteStockColumn.setCellValueFactory(new PropertyValueFactory<>("qteDemande"));
      refColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArticle().getRef()));
        lineItemsTable.setItems(lineItems);
        loadArticles();

        // Update subtotal and total when line items change
        lineItems.addListener((ListChangeListener<DemandeArticle>) change -> updateTotals());
        client.setText(ConnexionController.userLogin.getClient().getName());
    }

     private void loadArticles() {
        // Récupérer la liste des articles depuis le service
        List<Article> articles = articleService.show();
        
        // Extraire les libellés des articles
        ObservableList<String> articleLibelles = FXCollections.observableArrayList();
        for (Article article2 : articles) {
            articleLibelles.add(article2.getLibelle());
        }

        // Ajouter les libellés au ChoiceBox
        articleChoiceBox.setItems(articleLibelles);
    }

    @FXML
    public void addLineItem() {
        String articleBox = articleChoiceBox.getValue();
        Article article = articleService.findData().selectByLibelle(articleBox);
        String quantityText = quantityField.getText();

        if (article == null || quantityText.isEmpty()) {
            showAlert("Erreur", "Veuillez sélectionner un article et entrer une quantité.");
            return;
        }
        Integer quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (article == null || quantity > article.getQteStock()) {
                showAlert("Erreur de connexion", "La quantité est superieure à celui de l'article.");
                return;
              }
              demande.setMontant(0.0);
              article.setQteStock(article.getQteStock() -  quantity);
              articleService.findData().updateAllInt(article.getId(), "qteStock", article.getQteStock());
              DemandeArticle demandeArticle = new DemandeArticle();
                demandeArticle.setArticle(article);
                demandeArticle.setQteDemande(quantity);
                demandeArticle.setSomme(demandeArticle.getQteDemande() * article.getPrix());
                article.getDemandeArticles().add(demandeArticle);
                demande.setMontant(demande.getMontant() + demandeArticle.getSomme());
                demande.setClient(ConnexionController.userLogin.getClient());
                demande.getDemandeArticles().add(demandeArticle);
                demande.setEtatDeDemande(EtatDeDemande.Enc_cours);
                demandeArticle.setDemande(demande);
                lineItems.add(demandeArticle);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "La quantité doit être un nombre.");
            return;
        }
        quantityField.clear();
    }

    @FXML
    public void saveInvoice() {
        for (DemandeArticle demandeArticle : demande.getDemandeArticles()) {
            demandeArticleService.saveList(demandeArticle);
        }
        demandeService.saveList(demande);
    }

    private void updateTotals() {
        double total = demande.getMontant();
        totalLabe.setText(String.format("%.2f", total));
    }

   
}

