package com.ism.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ism.App;
import com.ism.core.Database.UserRepoListInt;
import com.ism.entities.User;
import com.ism.enums.Etat;
import com.ism.repositories.JPA.UserRepoJpa;
import com.ism.service.UserService;
import com.ism.service.UserServiceInt;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class UserBoutiquierController extends CoreController {
  
  @FXML
  private TableView<User> tableView;
  @FXML
  private TableColumn<User, String> loginColumn;
  @FXML
  private TableColumn<User, String> emailColumn;
  @FXML
  private TableColumn<User, String> roleColumn;
  @FXML
  private TableColumn<User, String> etatColumn1;
  @FXML
  private TableColumn<User, Void> actionsColumn;

  @FXML
  private Pagination pagination;

  @FXML
  private TextField searchField;

  @FXML
  private AnchorPane telPane;

  private UserRepoListInt userRepo;
  private UserServiceInt userService;


  private String filterUsers = "all";

  private ObservableList<User> clientList = FXCollections.observableArrayList();
  private static final int ROWS_PER_PAGE = 4;


  public UserBoutiquierController() {
        userRepo = new UserRepoJpa();
        userService = new UserService(userRepo);
    }

  @FXML
  public void initialize() {
      // Configurer les colonnes de texte
      loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
      emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
      roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole().getNomRole()));
      etatColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtat().toString()));

      // Ajouter des boutons à la colonne "Actions"
      addButtonToTable();

      // Charger les données
      loadClientData();

      // Assigner la liste observable au TableView
      tableView.setItems(clientList);

      initPagination();
  }

  @FXML
  private void handleUserButtonClick()  {
      filterUsers = "actif"; // Met à jour le filtre pour ne montrer que les utilisateurs
      loadClientData(); // Recharge les données avec le filtre
  }

  @FXML
  private void handleNoUserButtonClick()  {
    telPane.setVisible(true);
    telPane.setManaged(true);
  }

  @FXML
  private void handleAllUserButtonClick()  {
      filterUsers = "all"; // Remet le filtre à false pour afficher tous les
      loadClientData(); // Recharge toutes les données
  }

  @FXML
  private void search() {
      loadClientData();
  }


  private void loadClientData() {
      List<User> allClients = userService.show();
      List<User> filtreClient = new ArrayList<>();
      String search = searchField.getText();
      if (filterUsers.equals("actif")) {
          allClients.stream()
          .filter(data -> data.getEtat().equals(Etat.Activer))
          .forEach(filtreClient::add);
      }
      else if (filterUsers.equals("role")) {
          allClients.stream()
          .filter(data -> data.getEtat() == null)
          .forEach(filtreClient::add);
      } 
      else if (!search.isEmpty()) {
        userService.findData().selectAll().stream()
        .filter(data -> data.getRole().getNomRole().equals(search))
        .forEach(filtreClient::add);
      }
      else {
          filtreClient = allClients;
          System.out.println(filtreClient);
      }
      
      clientList.setAll(filtreClient);
      initPagination(); // Recharge la pagination après chaque filtre
      pagination.setCurrentPageIndex(0); // Réinitialise l'index de page
      searchField.clear();
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
      ObservableList<User> clientsSubList = FXCollections.observableArrayList(clientList.subList(start, end));
      tableView.setItems(clientsSubList);
  }

  // Méthode pour ajouter un bouton à chaque ligne de la colonne "Actions"
  private void addButtonToTable() {
      Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<>() {
          @Override
          public TableCell<User, Void> call(final TableColumn<User, Void> param) {
              return new TableCell<>() {

                 private final Button btnRefuser = new Button("Desactiver");
                 private final Button btnValider = new Button("Activer");
                 private final HBox buttonBox = new HBox(btnRefuser, btnValider);

                {
                    // Définir l'espacement entre les boutons
                    buttonBox.setSpacing(5);

                    // Ajouter l'action pour le bouton "Dettes"
                    btnRefuser.setOnAction((ActionEvent event) -> {
                      User user = getTableView().getItems().get(getIndex());
                      try {
                        handleRefuserButtonAction(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    });

                    // Ajouter l'action pour le bouton "Supprimer"
                    btnValider.setOnAction((ActionEvent event) -> {
                      User user = getTableView().getItems().get(getIndex());
                      try {
                        handleValiderButtonAction(user);
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

  // Action à exécuter lorsqu'on clique sur le bouton
   private void handleRefuserButtonAction(User user) throws IOException {   // Stocke le client dans la variable statique
      user.setEtat(Etat.Desactiver);
      userRepo.update(user);
      if (ConnexionController.hip.compareTo("Boutiquier") == 0) {
        App.setRoot("listerUserByBoutiquier");
      }
      else{
        App.setRoot("listerUser");
      }
    }

    private void handleValiderButtonAction(User user) throws IOException {   // Stocke le client dans la variable statique
      user.setEtat(Etat.Activer);
      userRepo.update(user);
      if (ConnexionController.hip.compareTo("Boutiquier") == 0) {
        App.setRoot("listerUserByBoutiquier");
      }
      else{
        App.setRoot("listerUser");
      }
  }


  @FXML
  private void create() throws IOException {
      App.setRoot("CreateUserByBoutiquier");
  }
  @FXML
  private void addClient() throws IOException {
      App.setRoot("CreateUserClientByBoutiquier");
  }

  @FXML
  private void create2() throws IOException {
      App.setRoot("CreateUser");
  }
  @FXML
  private void addClient2() throws IOException {
      App.setRoot("CreateUserClient");
  }

}
