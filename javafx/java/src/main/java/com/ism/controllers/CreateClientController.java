package com.ism.controllers;

import java.io.IOException;

import com.ism.App;
import com.ism.core.Database.ClientRepoListInt;
import com.ism.core.Database.UserRepoListInt;
import com.ism.entities.Client;
import com.ism.entities.Role;
import com.ism.entities.User;
import com.ism.enums.Etat;
import com.ism.repositories.JPA.ClientRepoJpa;
import com.ism.repositories.JPA.UserRepoJpa;
import com.ism.service.ClientService;
import com.ism.service.ClientServiceInt;
import com.ism.service.UserService;
import com.ism.service.UserServiceInt;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CreateClientController extends CoreController {


  @FXML
  private TextField name;
  @FXML
  private TextField tel;
  @FXML
  private TextField adresse;
  @FXML
  private TextField login;
  @FXML
  private TextField email;
  @FXML
  private TextField password;

  private ClientRepoListInt clientRepo;
  private ClientServiceInt clientService;
  private UserRepoListInt uRepoLisT;
  private UserServiceInt  uService;



  @FXML
  private CheckBox ajouterCompteCheckBox;

  @FXML
  private AnchorPane loginPane;

  public CreateClientController() {
    clientRepo = new ClientRepoJpa();
    uRepoLisT = new UserRepoJpa();
    clientService = new ClientService(clientRepo);
    uService = new UserService(uRepoLisT);
  }

  @FXML
  public void initialize() {
    // Ajouter un écouteur pour le changement d'état du checkbox
    ajouterCompteCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
        // Afficher/masquer loginPane en fonction de l'état du checkbox
        loginPane.setVisible(newValue);
        loginPane.setManaged(newValue);
    });
  }
   @FXML
    private void create() throws IOException {
        String nameField = name.getText();
        String telField = tel.getText();
        String adresseField = adresse.getText();
        String loginField = login.getText();
        String emailField = email.getText();
        String passwordField = password.getText();
        if (nameField.isEmpty() || telField.isEmpty() || adresseField.isEmpty()) {
            showAlert("Erreur de connexion", "Veuillez saisir votre nom complet, votre telephone et votre adresse.");
            return;
        }
        if ((clientRepo.selectBySurname(nameField) != null) ||(clientRepo.selectByPhone(telField)!= null)) {
            showAlert("Erreur de connexion", "Nom d'utilisateur ou telephone existe déjà.");
        }else{
          Client client = new Client();
          client.setName(nameField);
          client.setTel(telField);
          client.setAdresse(adresseField);
          if (!ajouterCompteCheckBox.isSelected()) {
            client.setUser(null);
            clientService.saveList(client);
          }
          else{
            if (loginField.isEmpty() || emailField.isEmpty() || passwordField.isEmpty()) {
              showAlert("Erreur de connexion", "Veuillez saisir votre login, votre email et votre password.");
              return;
            }
            if (uRepoLisT.selectForUser(loginField,"SelectByLogin","login")!= null || uRepoLisT.selectForUser(emailField,"SelectByEmail","email") != null || uRepoLisT.selectForUser(passwordField,"SelectByPassword","password") != null) {
              showAlert("Erreur de connexion", "L'un des champs saisit existe déjà.");
              
            }else{
              User user = new User();
              Role  role1 = new Role();
              user.setLogin(loginField);
              user.setEmail(emailField);
              user.setPassword(passwordField);
              role1.setNomRole("Client");
              role1.setId(3);
              user.setRole(role1);
              user.setEtat(Etat.Activer);
              user.setClient(client);
              client.setUser(user);
              clientService.saveList(client);
              uService.saveList(user);
              System.out.println(user);

            }
          }
          
          App.setRoot("listerClient");
        }
    }




}
