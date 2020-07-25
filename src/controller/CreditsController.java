package controller;

import application.Credits;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import util.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

public class CreditsController implements Initializable {

    @FXML
    private Button btnOkay;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnOkay.setOnMouseClicked((MouseEvent e) -> {
            Navigation.close(Credits.getStage());
        });
    }

}
