package com.ism.controllers;

import java.io.IOException;

import com.ism.App;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("");
    }
}
