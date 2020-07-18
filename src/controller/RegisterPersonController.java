package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Principal;
import sample.RegisterPerson;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterPersonController implements Initializable {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField psfPassword;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnRegister;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnCancel.setOnMouseClicked((MouseEvent e) -> {
            openPrincipal();
        });

        btnCancel.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                openPrincipal();
            }
        });

        btnRegister.setOnMouseClicked((MouseEvent e) -> {

        });

        btnRegister.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {

            }
        });

    }

    public void close() {
        RegisterPerson.getStage().close();
    }

    public void openPrincipal() {
        Principal p = new Principal();
        close();
        try {
            p.start(new Stage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
