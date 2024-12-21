package com.ism.controllers;

import javafx.fxml.FXML;

import java.io.IOException;

import com.ism.App;

public class SidebarClientController {
    
    
    
    @FXML
    private void goToDettes2() throws IOException {
        App.setRoot("listeDette");
    }
    
    
    @FXML
    private void goToDemandes2() throws IOException {
        App.setRoot("listeDemandeBoutiquier");
    }
    
    @FXML
    private void goToComptes() throws IOException {
        App.setRoot("listeComptes");
    }
}
