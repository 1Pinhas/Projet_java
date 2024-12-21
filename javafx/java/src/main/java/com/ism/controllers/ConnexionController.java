package com.ism.controllers;

import java.io.IOException;

import com.ism.App;
import com.ism.core.Database.UserRepoListInt;
import com.ism.entities.User;
import com.ism.repositories.JPA.UserRepoJpa;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConnexionController extends CoreController {

    @FXML
    private TextField loginField; // Champ pour le nom d'utilisateur
    @FXML
    private PasswordField passwordField; // Champ pour le mot de passe

    private UserRepoListInt uRepoLisT;
    public static String hip;
    public static User userLogin;

    


    public ConnexionController() {
        uRepoLisT = new UserRepoJpa();
    }




    @FXML
    private void connexion() throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();
        userLogin = uRepoLisT.selectForUser(login,"SelectByLogin","login");
        User userPassword = uRepoLisT.selectForUser(password,"SelectByPassword","password");
        hip = userLogin.getRole().getNomRole();

        if (login.isEmpty() || password.isEmpty()) {
            showAlert("Erreur de connexion", "Veuillez saisir votre nom d'utilisateur et mot de passe.");
            return;
        }
        if ((userLogin == null) ||(userPassword== null)) {
            showAlert("Erreur de connexion", "Nom d'utilisateur ou mot de passe incorrect.");
        }
        else if (!userLogin.equals(userPassword)) {
            showAlert("Erreur de connexion", "Le login et le mot de passe ne correspondent pas.");
        }
        else{
            if (userLogin.getRole().getNomRole().compareTo("Boutiquier") == 0) {
                App.setRoot("listerClient");
            }
            if (userLogin.getRole().getNomRole().compareTo("Admin") == 0) {
                App.setRoot("listerUser");
            }
            if (userLogin.getRole().getNomRole().compareTo("Client") == 0) {
                App.setRoot("listeDetteUserClient");
            }
        }
    }


    


}
