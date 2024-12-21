package com.ism.controllers;

import java.io.IOException;

import com.ism.App;
import com.ism.core.Database.UserRepoListInt;
import com.ism.entities.Role;
import com.ism.entities.User;
import com.ism.enums.Etat;
import com.ism.repositories.JPA.UserRepoJpa;
import com.ism.service.UserService;
import com.ism.service.UserServiceInt;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateUserByBoutiquierController extends CoreController{
  
  @FXML
  private TextField login;
  @FXML
  private TextField email;
  @FXML
  private TextField password;
  @FXML
  private TextField role;

  private UserRepoListInt uRepoLisT;
  private UserServiceInt  uService;

  public CreateUserByBoutiquierController() {
    uRepoLisT = new UserRepoJpa();
    uService = new UserService(uRepoLisT);
  }

  @FXML
    private void create() throws IOException {
        String loginField = login.getText();
        String emailField = email.getText();
        String passwordField = password.getText();
        String roleField = role.getText();
        if (loginField.isEmpty() || emailField.isEmpty() || passwordField.isEmpty() || roleField.isEmpty()) {
          showAlert("Erreur de connexion", "Veuillez saisir votre login, votre email, votre password et votre role.");
          return;
        }
        if ((roleField.compareTo("Boutiquier") != 0) && (roleField.compareTo("Admin") != 0)) {
          showAlert("Erreur de connexion", "Le role est incorrect.");
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
          if (roleField.compareTo("Boutiquier") == 0) {
            role1.setNomRole(roleField);
            role1.setId(1);
          }
          else{
            role1.setNomRole(roleField);
            role1.setId(2);
          }
          user.setRole(role1);
          uService.saveList(user);

        }
      App.setRoot("listerUserByBoutiquier");
        
    }





}
