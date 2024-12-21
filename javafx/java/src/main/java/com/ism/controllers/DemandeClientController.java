package com.ism.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ism.App;
import com.ism.core.Database.DemandeRepoListInt;
import com.ism.entities.Demande;
import com.ism.entities.User;
import com.ism.enums.EtatDeDemande;
import com.ism.repositories.JPA.DemandeRepoJpa;
import com.ism.service.DemandeService;
import com.ism.service.DemandeServiceInt;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DemandeClientController extends CoreController {
  
  @FXML
  private TableView<Demande> tableView;

  @FXML
  private TableColumn<Demande, Integer> idColumn;

  @FXML
  private TableColumn<Demande, LocalDate> dateColumn;
  @FXML
  private TableColumn<Demande, String> nomClientColumn;

  @FXML
  private TableColumn<Demande, Void> actionsColumn1;

  @FXML
  private Pagination pagination;
  @FXML
  private TextField searchField;

  private DemandeRepoListInt  demandeRepo;
  private DemandeServiceInt  demandeService;
  public static List<Demande> alldemandes;
  public static Demande sessionDemande;


  private String filterdettes = "all";


  private ObservableList<Demande> clientList = FXCollections.observableArrayList();
  private static final int ROWS_PER_PAGE = 4;

  public DemandeClientController() {
      demandeRepo = new DemandeRepoJpa();
      demandeService = new DemandeService(demandeRepo);
  }

  @FXML
  public void initialize() {
      // Configurer les colonnes de texte
      idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
      dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreate"));
      nomClientColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClient().getName()));

      addButtonToTable();

      // Charger les données
      loadClientData();

      // Assigner la liste observable au TableView
      tableView.setItems(clientList);

      initPagination();
  }

  @FXML
  private void handleSoldButtonClick()  {
    filterdettes = "encours"; // Met à jour le filtre pour ne montrer que les utilisateurs
    loadClientData(); // Recharge les données avec le filtre
  }

  @FXML
  private void handleNoSoldButtonClick()  {
    filterdettes = "annuler"; // Remet le filtre à false pour afficher tous les clients
    loadClientData(); // Recharge toutes les données
  }

  @FXML
  private void handleAllSoldButtonClick()  {
    filterdettes = "all"; // Remet le filtre à false pour afficher tous les
    loadClientData(); // Recharge toutes les données
  }

  @FXML
  private void search() {
      loadClientData();
  }


   private void loadClientData() {
      List<Demande> filtreDette = new ArrayList<>();
      // alldemandes = demandeService.show();
      alldemandes = ConnexionController.userLogin.getClient().getDemandes();
      String search = searchField.getText();
      if (filterdettes.equals("encours")) {
        alldemandes.stream()
          .filter(demande -> demande.getEtatDeDemande().equals(EtatDeDemande.Enc_cours))
          .forEach(filtreDette::add);
          System.out.println(filtreDette);
      }
      else if (filterdettes.equals("annuler")) {
        alldemandes.stream()
          .filter(demande -> demande.getEtatDeDemande().equals(EtatDeDemande.Annuler))
          .forEach(filtreDette::add);
          System.out.println(filtreDette);
      } 
      else if (!search.isEmpty()) {
        if (!search.matches("\\d+")) {
          showAlert("Erreur de connexion", "Entrez des entiers.");
        }
        else{
          // sessionDemande =  demandeService.findData().selectById(Integer.valueOf(search));
          // filtreDette = sessionDemande.getClient().getDemandes().stream()
          // .collect(Collectors.toList());
          sessionDemande =  demandeService.findData().selectById(Integer.valueOf(search));
          sessionDemande.getClient().getDemandes().stream()
          .filter(demande -> demande.getEtatDeDemande().equals(EtatDeDemande.Enc_cours))
          .forEach(filtreDette::add);                           // Collecte les dettes dans filtreDette
          // alldemandes = filtreDette;
        }
      }
      // else if (filterdettes.equals("all")) {
      //   alldemandes.stream()
      //     .filter(demande -> !demande.getEtatDeDemande().equals(EtatDeDemande.Annuler) || !demande.getEtatDeDemande().equals(EtatDeDemande.Valider) )
      //     .forEach(filtreDette::add);
      // }
      else{
        alldemandes.stream()
          .filter(demande -> demande.getEtatDeDemande().equals(EtatDeDemande.Enc_cours) )
          .forEach(filtreDette::add);
      }
      
      clientList.setAll(filtreDette);
      initPagination(); // Recharge la pagination après chaque filtre
      pagination.setCurrentPageIndex(0); // Réinitialise l'index de page
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
      ObservableList<Demande> clientsSubList = FXCollections.observableArrayList(clientList.subList(start, end));
      tableView.setItems(clientsSubList);
  }

  @FXML
  private void handleTreatButtonClick() throws IOException{
    App.setRoot("treat");
  }
  @FXML
  private void handleArticleButtonClick() throws IOException{
    App.setRoot("voirArticleDemande");
  }

  @FXML
  private void handleCreateDemandeButtonClick(ActionEvent event) throws IOException{
    // App.setRoot("createDemandeTest");
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ism/views/createDemandeTest.fxml"));
      javafx.scene.Parent root = loader.load();
      Stage stage = new Stage();
      stage.setTitle("Ajouter une demande");
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setScene(new Scene(root, 451, 500));
      stage.showAndWait();
  } catch (Exception e) {
      e.printStackTrace();
  }
  
  }

  

  // Méthode pour ajouter un bouton à chaque ligne de la colonne "Actions"
    private void addButtonToTable() {
        Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Demande, Void> call(final TableColumn<Demande, Void> param) {
                return new TableCell<>() {

                 private final Button btn = new Button("Relance");

                {

                      btn.setOnAction((ActionEvent event) -> {
                        Demande  demande = getTableView().getItems().get(getIndex());
                        handleRelanceButtonClick(demande);
                        
                      });
                     
                    
                    
                }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        actionsColumn1.setCellFactory(cellFactory);
    }

   

  private void handleRelanceButtonClick(Demande demande) {   // Stocke le client dans la variable statique
    demande.setEtatDeDemande(EtatDeDemande.Enc_cours);
    demandeRepo.update(demande);
}











}
