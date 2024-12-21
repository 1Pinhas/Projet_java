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
import javafx.scene.control.TextField;

public class CreateUserClientByBoutiquierController extends CoreController{
  @FXML
  private TextField login;
  @FXML
  private TextField email;
  @FXML
  private TextField password;
  @FXML
  private TextField search;

  private UserRepoListInt uRepoLisT;
  private UserServiceInt  uService;
  private ClientServiceInt clientService;
  private ClientRepoListInt clientRepo;

  public CreateUserClientByBoutiquierController() {
    uRepoLisT = new UserRepoJpa();
    uService = new UserService(uRepoLisT);
    clientRepo = new ClientRepoJpa();
    clientService = new ClientService(clientRepo);
  }

  @FXML
    private void create() throws IOException {
        String loginField = login.getText();
        String emailField = email.getText();
        String passwordField = password.getText();
        String searchField = search.getText(); 
        Client client = clientService.findData().selectByPhone(searchField);
        if (loginField.isEmpty() || emailField.isEmpty() || passwordField.isEmpty() || searchField.isEmpty()) {
          showAlert("Erreur de connexion", "Veuillez saisir votre login, votre email, votre password et votre role.");
          return;
        }
        if (client == null) {
          showAlert("Erreur de connexion", "Veuillez saisir le bon numero de téléphone.");
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
            user.setEtat(Etat.Activer);
            role1.setNomRole("Client");
            role1.setId(3);
            user.setRole(role1);
            user.setClient(client);
            client.setUser(user);
            clientService.findData().update(client);
            uService.saveList(user);

          }
        App.setRoot("listerUserByBoutiquier");
        
    }
}
