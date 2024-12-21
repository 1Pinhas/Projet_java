package com.ism.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ism.App;
import com.ism.core.Database.ArticleRepoListInt;
import com.ism.core.Database.DetteRepoListInt;
import com.ism.entities.DemandeArticle;
import com.ism.entities.Commande;
import com.ism.enums.EtatDette;
import com.ism.repositories.JPA.ArticleRepoJpa;
import com.ism.repositories.JPA.DetteRepoJpa;
import com.ism.service.ArticleService;
import com.ism.service.ArticleServiceInt;
import com.ism.service.DetteServceInt;
import com.ism.service.DetteService;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DetailController extends CoreController {
 

  @FXML
  private TableView<DemandeArticle> tableView;
  @FXML
  private TableColumn<DemandeArticle, String> articleColumn;
  @FXML
  private TableColumn<DemandeArticle, Double> prixColumn;
  @FXML
  private TableColumn<DemandeArticle, Integer> qteColumn;

  @FXML
  private Pagination pagination;


  @FXML
  private TableView<DemandeArticle> tableView2;
  @FXML
  private TableColumn<DemandeArticle, LocalDate> dateColumn;
  @FXML
  private TableColumn<DemandeArticle, Double> montantColumn;

  @FXML
  private Pagination pagination2;

  @FXML
  private TextField libelleField;
  
  private ObservableList<DemandeArticle> clientList = FXCollections.observableArrayList();
  private ObservableList<DemandeArticle> clientList2 = FXCollections.observableArrayList();
  private static final int ROWS_PER_PAGE = 4;
  private List<DemandeArticle> details = new ArrayList<>();
  private ArticleRepoListInt  articleRepoList;
  private ArticleServiceInt  articleService;
  private DetteRepoListInt   detteRepoList;
  private DetteServceInt    detteService;


  public DetailController() {
    this.articleRepoList = new ArticleRepoJpa();
    this.articleService = new ArticleService(articleRepoList);
    this.detteRepoList = new DetteRepoJpa();
    this.detteService = new DetteService(detteRepoList);
  }
  

  
  @FXML
  public void initialize() {
    // System.out.println(DetteClientController.selectedDette);
      // Configurer les colonnes de texte
      articleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArticle().getLibelle()));
      prixColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getArticle().getPrix()));
      qteColumn.setCellValueFactory(new PropertyValueFactory<>("qteDemande"));


      // Charger les données
      loadClientData();

      // Assigner la liste observable au TableView
      tableView.setItems(clientList);

      initPagination(pagination,clientList,tableView);
      
      initialize2();
  }

  @FXML
  private void libelleArticle() {
      loadClientData2();
  }

  @FXML
  public void initialize2() {
      // Configurer les colonnes de texte
      dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreate"));
      montantColumn.setCellValueFactory(new PropertyValueFactory<>("somme"));


      // Charger les données
      loadClientData2();

      // Assigner la liste observable au TableView
      tableView2.setItems(clientList2);

      initPagination(pagination2,clientList2,tableView2);
  }


  private void loadClientData() {
      List<DemandeArticle> filtreDette = new ArrayList<>();
      if (ConnexionController.hip.compareTo("Boutiquier") == 0 || ConnexionController.hip.compareTo("Admin") == 0) {
        if (DetteController.selectedDette != null) {
          details = DetteController.selectedDette.getDemande().getDemandeArticles();
          filtreDette = details.stream()
          .collect(Collectors.toList());
        }
      }
      else{
        if (DetteClientController.selectedDette != null) {
          details = DetteClientController.selectedDette.getDemande().getDemandeArticles();
          filtreDette = details.stream()
          .collect(Collectors.toList());
        }
      }
      // if (DetteClientController.selectedDette != null) {
      //   filtreDette = details.stream()
      //   .collect(Collectors.toList());
      // }
      
      clientList.setAll(filtreDette);
      initPagination(pagination,clientList,tableView); // Recharge la pagination après chaque filtre
      pagination.setCurrentPageIndex(0); // Réinitialise l'index de page
  }

  private void loadClientData2() {
    List<DemandeArticle> filtreDette = new ArrayList<>();
    String search = libelleField.getText();
    if (!search.isEmpty()) {
      if (articleService.findData().selectByLibelle(search) == null) {
        showAlert("Erreur de connexion", "Veuillez saisir le libelle du produit.");
        return;
      }
      else{
        details.stream()
        .filter(data -> data.getArticle().getLibelle().equals(search))
        .forEach(filtreDette::add);
        System.out.println(filtreDette);
      }
    }
    
    clientList2.setAll(filtreDette);
    System.out.println(clientList2);
    initPagination(pagination2,clientList2,tableView2); // Recharge la pagination après chaque filtre
    pagination2.setCurrentPageIndex(0); // Réinitialise l'index de page
}

  

  private void initPagination(Pagination pagina,ObservableList<DemandeArticle> list, TableView<DemandeArticle> table) {
      int pageCount = (int) Math.ceil((double) list.size() / ROWS_PER_PAGE);
      pagina.setPageCount(pageCount);
      pagina.setCurrentPageIndex(0);

      // Définir l'action de changement de page
      pagina.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> updateTableView(newIndex.intValue(),table,list));
      
      // Charger la première page
      updateTableView(0,table,list);
  }

  private void updateTableView(int pageIndex, TableView<DemandeArticle> tableView, ObservableList<DemandeArticle> clientList) {
      int start = pageIndex * ROWS_PER_PAGE;
      int end = Math.min(start + ROWS_PER_PAGE, clientList.size());
      ObservableList<DemandeArticle> clientsSubList = FXCollections.observableArrayList(clientList.subList(start, end));
      tableView.setItems(clientsSubList);
  }

  @FXML
  private void archiver(){
    Commande dette = DetteController.selectedDette;
    if (dette.getMontant().equals(dette.getMontantVerser())) {
      dette.setEtat(EtatDette.Archiver);
      detteService.findData().update(dette);
      showAlert("Add", "😂😂👌");
    }
    else{
      showAlert("Erreur de connexion", "La dette n'est pas soldée.");
    }
  }


































}
