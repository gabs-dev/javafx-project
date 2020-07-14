package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnLogin.setOnMouseClicked((MouseEvent e) -> {
            System.out.println("Entrou");
        });

        btnExit.setOnMouseClicked((MouseEvent e) -> {
            System.out.println("Saiu");
        });

    }

}
