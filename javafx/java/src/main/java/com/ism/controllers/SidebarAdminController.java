package com.ism.controllers;

import javafx.fxml.FXML;

import java.io.IOException;

import com.ism.App;

public class SidebarAdminController {
    
    
    @FXML
    private void goToDettes2() throws IOException {
        App.setRoot("listeDette");
    }
    
    @FXML
    private void goToArticles() throws IOException {
        App.setRoot("enregistrerPaiement");
    }
    
    
    @FXML
    private void goToComptes() throws IOException {
        App.setRoot("listeComptes");
    }

    @FXML
    private void deconnexion() throws IOException {
        App.setRoot("connexion");
    }


}
