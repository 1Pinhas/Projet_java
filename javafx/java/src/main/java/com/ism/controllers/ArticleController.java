package com.ism.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ism.App;
import com.ism.core.Database.ArticleRepoListInt;
import com.ism.entities.Article;
import com.ism.repositories.JPA.ArticleRepoJpa;
import com.ism.service.ArticleService;
import com.ism.service.ArticleServiceInt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ArticleController extends CoreController {
  
    @FXML
    private TableView<Article> tableView;
    @FXML
    private TableColumn<Article, String> libelleColumn;
    @FXML
    private TableColumn<Article, Double> prixColumn;
    @FXML
    private TableColumn<Article, Integer> qteStockColumn;
    @FXML
    private TableColumn<Article, String> refColumn1;  // Utilisation de Void pour l'absence de données
    @FXML
    private TableColumn<Article, Void> actionsColumn;

    @FXML
    private Pagination pagination;
    @FXML
    private TextField searchField;

    private ArticleRepoListInt articleRepo;
    private ArticleServiceInt  articleService;
    public static Article selectedArticle;





    private ObservableList<Article> clientList = FXCollections.observableArrayList();
    private static final int ROWS_PER_PAGE = 4;

    public ArticleController() {
      articleRepo = new ArticleRepoJpa();
      articleService = new ArticleService(articleRepo);
    }

  @FXML
  public void initialize() {
      // Configurer les colonnes de texte
      libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
      prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
      qteStockColumn.setCellValueFactory(new PropertyValueFactory<>("qteStock"));
      refColumn1.setCellValueFactory(new PropertyValueFactory<>("ref"));

      // Ajouter des boutons à la colonne "Actions"
      addButtonToTable();

      // Charger les données
      loadClientData();

      // Assigner la liste observable au TableView
      tableView.setItems(clientList);

      initPagination();
  }

  @FXML
  private void search() {
      loadClientData();
  }

private void loadClientData() {
        List<Article> allClients = articleService.show();
        List<Article> filtreClient = new ArrayList<>();
        String search = searchField.getText();
        if (!search.isEmpty()) {
            if (articleService.findData().selectByLibelle(search) == null) {
                showAlert("Erreur de connexion", "Veuillez saisir le bon libelle.");
                return;
            }
            else{
                allClients.stream()
                .filter(data -> data.getLibelle().equals(search))
                .forEach(filtreClient::add);
                System.out.println(filtreClient);
            }
        }
        else {
            filtreClient = allClients;
        }
        
        clientList.setAll(filtreClient);
        initPagination(); // Recharge la pagination après chaque filtre
        pagination.setCurrentPageIndex(0); // Réinitialise l'index de page
        // System.out.println("Filtered client list: " + clientList);
    }
    

    private void initPagination() {
        int pageCount = (int) Math.ceil((double) clientList.size() / ROWS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0);

        // Définir l'action de changement de page
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> updateTableView(newIndex.intValue()));
        
        // Charger la première page
        updateTableView(0);
    }

    private void updateTableView(int pageIndex) {
        int start = pageIndex * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, clientList.size());
        ObservableList<Article> clientsSubList = FXCollections.observableArrayList(clientList.subList(start, end));
        tableView.setItems(clientsSubList);
    }

  // Méthode pour ajouter un bouton à chaque ligne de la colonne "Actions"
  private void addButtonToTable() {
      Callback<TableColumn<Article, Void>, TableCell<Article, Void>> cellFactory = new Callback<>() {
          @Override
          public TableCell<Article, Void> call(final TableColumn<Article, Void> param) {
              return new TableCell<>() {

                private final Button btnRefuser = new Button("Update");
                private final Button btnValider = new Button("Supprimer");
                private final HBox buttonBox = new HBox(btnRefuser, btnValider);

               {
                   // Définir l'espacement entre les boutons
                   buttonBox.setSpacing(5);

                   // Ajouter l'action pour le bouton "Dettes"
                   btnRefuser.setOnAction((ActionEvent event) -> {
                    Article article = getTableView().getItems().get(getIndex());
                     try {
                       handleRefuserButtonAction(article);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   });

                   // Ajouter l'action pour le bouton "Supprimer"
                   btnValider.setOnAction((ActionEvent event) -> {
                    Article article = getTableView().getItems().get(getIndex());
                     try {
                       handleValiderButtonAction(article);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                     
                   });
               }

                  @Override
                  public void updateItem(Void item, boolean empty) {
                      super.updateItem(item, empty);
                      if (empty) {
                          setGraphic(null);
                      } else {
                          setGraphic(buttonBox);
                      }
                  }
              };
          }
      };

      actionsColumn.setCellFactory(cellFactory);
  }

  private void handleRefuserButtonAction(Article article) throws IOException {
    selectedArticle = article;
    App.setRoot("updateArticle");
  }

  private void handleValiderButtonAction(Article article) throws IOException {   // Stocke le client dans la variable statique
    
}


  @FXML
  private void create() throws IOException {
      App.setRoot("CreateArticle");
  }
















}
