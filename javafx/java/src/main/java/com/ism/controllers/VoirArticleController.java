package com.ism.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ism.entities.DemandeArticle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VoirArticleController {
  
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

  private ObservableList<DemandeArticle> clientList = FXCollections.observableArrayList();
  private static final int ROWS_PER_PAGE = 4;
  private List<DemandeArticle> demandeArticles = DemandeBoutiquierController.sessionDemande.getDemandeArticles();

  

  @FXML
  public void initialize() {
      // Configurer les colonnes de texte
      articleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArticle().getLibelle()));
      prixColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getArticle().getPrix()));
      qteColumn.setCellValueFactory(new PropertyValueFactory<>("qteDemande"));


      // Charger les données
      loadClientData();

      // Assigner la liste observable au TableView
      tableView.setItems(clientList);

      initPagination(pagination,clientList,tableView);
  }


  private void loadClientData() {
      List<DemandeArticle> filtreDette = new ArrayList<>();
      if (demandeArticles != null) {
        filtreDette = demandeArticles.stream()
        .collect(Collectors.toList());
      }
      
      clientList.setAll(filtreDette);
      initPagination(pagination,clientList,tableView); // Recharge la pagination après chaque filtre
      pagination.setCurrentPageIndex(0); // Réinitialise l'index de page
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



}
