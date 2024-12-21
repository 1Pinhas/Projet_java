package com.ism.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CoreController {



  protected void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
