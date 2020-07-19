package controller;

import dao.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Person;
import sample.Login;
import sample.Principal;
import util.Alerts;

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
            login();
        });

        btnLogin.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                login();
            }
        });

        btnExit.setOnMouseClicked((MouseEvent e) -> {
            close();
        });

        btnExit.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                close();
            }
        });

        txtPassword.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                login();
            }
        });

    }

    private void close() {
        Login.getStage().close();
    }

    private void login() {
        PersonDao dao = new PersonDao();
        Person p;
        if (!(txtUser.getText().equals("")) && !(txtPassword.getText().equals(""))) {
            p = dao.findByEmail(txtUser.getText());
            if (p != null && p.getPassword().equals(txtPassword.getText())) {
                Principal principal = new Principal();
                close();
                try {
                    principal.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alerts.showAlert("Erro", null, "Usuário ou senha inválido(s)", AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Erro", null, "Preecha o(s) campo(s) de email e/ou senha", AlertType.ERROR);
        }
    }

}
