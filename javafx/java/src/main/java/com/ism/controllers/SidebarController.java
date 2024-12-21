package com.ism.controllers;

import javafx.fxml.FXML;

import java.io.IOException;

import com.ism.App;

public class SidebarController {
    
    @FXML
    private void goToClients() throws IOException {
        App.setRoot("listerClient");
    }
    
    @FXML
    private void goToDettes() throws IOException {
        App.setRoot("listeDette");
    }
    
    @FXML
    private void goToPaiements() throws IOException {
        App.setRoot("enregistrerPaiement");
    }
    
    @FXML
    private void goToDemandes() throws IOException {
        App.setRoot("listeDemandeBoutiquier");
    }
    
    @FXML
    private void goToDemandes2() throws IOException {
        App.setRoot("listeDemandeClient");
    }

    @FXML
    private void goToComptes() throws IOException {
        App.setRoot("listerUserByBoutiquier");
    }

    @FXML
    private void goToDettes2() throws IOException {
        App.setRoot("listeDetteUserAdmin");
    }

    @FXML
    private void goToDettes3() throws IOException {
        App.setRoot("listeDetteUserClient");
    }
    
    @FXML
    private void goToArticles() throws IOException {
        App.setRoot("listerArticle");
    }
    
    
    @FXML
    private void goToComptes2() throws IOException {
        App.setRoot("listerUser");
    }

    @FXML
    private void deconnexion() throws IOException {
        App.setRoot("connexion");
    }





}
