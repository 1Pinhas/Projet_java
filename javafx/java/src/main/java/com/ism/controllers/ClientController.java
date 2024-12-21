package com.ism.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ism.App;
import com.ism.core.Database.ClientRepoListInt;
import com.ism.entities.Client;
import com.ism.repositories.JPA.ClientRepoJpa;
import com.ism.service.ClientService;
import com.ism.service.ClientServiceInt;

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
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientController extends CoreController {

    @FXML
    private TableView<Client> tableView;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, String> telColumn;
    @FXML
    private TableColumn<Client, String> adresseColumn;
    @FXML
    private TableColumn<Client, Void> actionsColumn;  // Utilisation de Void pour l'absence de données

    @FXML
    private Pagination pagination;
    @FXML
    private TextField searchField;

    private ClientServiceInt clientService;
    private ClientRepoListInt clientRepo;
    private String filterUsers = "all";
    public static Client selectedClient;

    // Liste observable pour stocker les clients
    private ObservableList<Client> clientList = FXCollections.observableArrayList();
    private static final int ROWS_PER_PAGE = 4; // Nombre de lignes par page

    public ClientController() {
        clientRepo = new ClientRepoJpa();
        clientService = new ClientService(clientRepo);
    }
    

    @FXML
    public void initialize() {
        // Configurer les colonnes de texte
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));

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
        filterUsers = "user"; // Met à jour le filtre pour ne montrer que les utilisateurs
        loadClientData(); // Recharge les données avec le filtre
    }

    @FXML
    private void handleNoUserButtonClick()  {
        filterUsers = "nouser"; // Remet le filtre à false pour afficher tous les clients
        loadClientData(); // Recharge toutes les données
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
        List<Client> allClients = clientService.show();
        List<Client> filtreClient = new ArrayList<>();
        String search = searchField.getText();
        if (filterUsers.equals("user")) {
            allClients.stream()
            .filter(data -> data.getUser() != null)
            .forEach(filtreClient::add);
        }
        else if (filterUsers.equals("nouser")) {
            allClients.stream()
            .filter(data -> data.getUser() == null)
            .forEach(filtreClient::add);
        } 
        else if (!search.isEmpty()) {
            if (clientService.findData().selectByPhone(search) == null) {
                showAlert("Erreur de connexion", "Veuillez saisir le bon numero de téléphone.");
                return;
            }
            else{
                allClients.stream()
                .filter(data -> data.getTel().equals(search))
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
        ObservableList<Client> clientsSubList = FXCollections.observableArrayList(clientList.subList(start, end));
        tableView.setItems(clientsSubList);
    }

    // Méthode pour ajouter un bouton à chaque ligne de la colonne "Actions"
    private void addButtonToTable() {
        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Client, Void> call(final TableColumn<Client, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Dettes");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Client client = getTableView().getItems().get(getIndex());
                            try {
                                handleActionButton(client);
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
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        actionsColumn.setCellFactory(cellFactory);
    }

    // Action à exécuter lorsqu'on clique sur le bouton
    private void handleActionButton(Client client) throws IOException {
        selectedClient = client;  // Stocke le client dans la variable statique
        App.setRoot("listeDette");  // Navigue vers la page listeDette
    }


    @FXML
    private void create() throws IOException {
        App.setRoot("createClient");
    }





   
}
