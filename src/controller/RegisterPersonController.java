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
import jdbc.exception.DbException;
import model.Person;
import application.Principal;
import application.RegisterPerson;
import util.Alerts;

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
    private PasswordField psfConfirmPassword;

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
            registerPerson();
        });

        btnRegister.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                registerPerson();
            }
        });

    }

    private void registerPerson() {
        String name = txtName.getText(),
                email = txtEmail.getText(),
                password = psfPassword.getText(),
                confPassword = psfConfirmPassword.getText();

        if(password.equals(confPassword)) {
            Person p = new Person(name, email, password );
            PersonDao dao = new PersonDao();
            try {
                dao.add(p);
                openPrincipal();
                Alerts.showAlert("Sucesso", null, "Usuário cadastrado com sucesso", AlertType.INFORMATION);
                close();
            } catch (DbException e) {
                String message = "Erro ao cadastrar usuário";
                message += "\n" + e.getMessage();
                Alerts.showAlert("Erro", null, message, AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Erro", null, "As senhas não coincidem!", AlertType.ERROR);
        }
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
