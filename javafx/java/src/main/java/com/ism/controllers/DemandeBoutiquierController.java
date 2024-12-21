package com.ism.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ism.App;
import com.ism.core.Database.DemandeRepoListInt;
import com.ism.core.Database.DetteRepoListInt;
import com.ism.entities.Demande;
import com.ism.entities.Commande;
import com.ism.enums.EtatDeDemande;
import com.ism.enums.EtatDette;
import com.ism.repositories.JPA.DemandeRepoJpa;
import com.ism.repositories.JPA.DetteRepoJpa;
import com.ism.service.DemandeService;
import com.ism.service.DemandeServiceInt;
import com.ism.service.DetteServceInt;
import com.ism.service.DetteService;

import javafx.beans.property.SimpleObjectProperty;
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

public class DemandeBoutiquierController extends CoreController {
  
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
    private DetteRepoListInt  detteRepo;
    private DetteServceInt detteService;
  public static List<Demande> alldemandes;
  public static Demande sessionDemande;


  private String filterdettes = "all";


  private ObservableList<Demande> clientList = FXCollections.observableArrayList();
  private static final int ROWS_PER_PAGE = 4;

  public DemandeBoutiquierController() {
      demandeRepo = new DemandeRepoJpa();
      demandeService = new DemandeService(demandeRepo);
      detteRepo = new DetteRepoJpa();
      detteService = new DetteService(detteRepo);
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
      alldemandes = demandeService.show();
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
          sessionDemande =  demandeService.findData().selectById(Integer.valueOf(search));
          filtreDette = sessionDemande.getClient().getDemandes().stream()
          .collect(Collectors.toList());                              // Collecte les dettes dans filtreDette
          // alldemandes = filtreDette;
        }
      }
      // else if (filterdettes.equals("all")) {
      //   filtreDette = alldemandes;
      // }
      else{
        // filtreDette = alldemandes;
        alldemandes.stream()
          .filter(demande -> demande.getEtatDeDemande().equals(EtatDeDemande.Enc_cours))
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


  

  // Méthode pour ajouter un bouton à chaque ligne de la colonne "Actions"
    private void addButtonToTable() {
        Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Demande, Void> call(final TableColumn<Demande, Void> param) {
                return new TableCell<>() {

                 private final Button btnRefuser = new Button("Refuser");
                 private final Button btnValider = new Button("Valider");
                 private final HBox buttonBox = new HBox(btnRefuser, btnValider);

                {
                    // Définir l'espacement entre les boutons
                    buttonBox.setSpacing(5);

                      // Ajouter l'action pour le bouton "Dettes"
                    btnRefuser.setOnAction((ActionEvent event) -> {
                      Demande demande = getTableView().getItems().get(getIndex());
                      handleRefuserButtonAction(demande);
                    });

                    // Ajouter l'action pour le bouton "Supprimer"
                    btnValider.setOnAction((ActionEvent event) -> {
                      Demande  demande = getTableView().getItems().get(getIndex());
                      handleValiderButtonAction(demande);
                      
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

        actionsColumn1.setCellFactory(cellFactory);
    }

    // Action à exécuter lorsqu'on clique sur le bouton
    private void handleRefuserButtonAction(Demande demande) {   // Stocke le client dans la variable statique
      demande.setEtatDeDemande(EtatDeDemande.Annuler);
      demandeRepo.update(demande);
    }

    private void handleValiderButtonAction(Demande demande) {   // Stocke le client dans la variable statique
      Commande dette = new Commande();
      demande.setEtatDeDemande(EtatDeDemande.Valider);
      demandeRepo.update(demande);
      dette.setMontant(demande.getMontant());
      dette.setMontantVerser(0.0);
      dette.setClient(demande.getClient());
      dette.setDemande(demande);
      dette.setEtat(EtatDette.Nonarchiver);
      detteService.saveList(dette);

  }











}
