package controller;

import application.Credits;
import application.ForgotPassword;
import application.Login;
import application.Principal;
import dao.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.Person;
import util.Alerts;
import util.Navigation;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Circle userPhoto;

    @FXML
    private Button btnCredits;

    @FXML
    private Label lblForgetPassword;

    private static Person logged;

    private PersonDao dao;

    private String standardPhoto = "/resources/image/system/icon-photo.png";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userPhoto.setFill(new ImagePattern(new Image(standardPhoto)));

        dao = new PersonDao();
        List<Person> list = dao.findAll();

        btnLogin.setOnMouseClicked((MouseEvent e) -> {
            login();
        });

        txtUser.setOnKeyReleased((KeyEvent e) -> {
            Person p = getUser(list);
            if (p != null) {
                if (p.getPhoto().equals(standardPhoto)) {
                    userPhoto.setFill(new ImagePattern(new Image(standardPhoto)));
                } else {
                    userPhoto.setFill(new ImagePattern(new Image("file:///" + p.getPhoto())));
                }
            } else {
                userPhoto.setFill(new ImagePattern(new Image(standardPhoto)));
            }
        });

        btnLogin.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                login();
            }
        });

        txtPassword.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                login();
            }
        });

        btnCredits.setOnMouseClicked((MouseEvent e) -> {
            openCredits();
        });

        lblForgetPassword.setOnMouseClicked((MouseEvent e) -> {
            openForgetPassword();
        });

    }

    private void openCredits() {
        Navigation.openScreen(new Credits());
    }

    private void openForgetPassword() {
        Navigation.openScreen(new ForgotPassword());
    }

    private void login() {
        dao = new PersonDao();
        Person p;
        if (!(txtUser.getText().equals("")) && !(txtPassword.getText().equals(""))) {
            p = dao.findByEmail(txtUser.getText());
            if (p != null && p.getPassword().equals(txtPassword.getText())) {
                setLogged(p);
                Navigation.openScreen(new Principal());
                Navigation.close(Login.getStage());
            } else {
                Alerts.showAlert("Erro", null, "Usuário ou senha inválido(s)", AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Erro", null, "Preecha o(s) campo(s) de email e/ou senha", AlertType.ERROR);
        }
    }

    private Person getUser(List<Person> list) {
        for(Person p : list) {
            if (p.getEmail().equals(txtUser.getText())) {
                return p;
            }
        }
        return null;
    }

    public static Person getLogged() {
        return logged;
    }

    public static void setLogged(Person p) {
        logged = p;
    }

}
