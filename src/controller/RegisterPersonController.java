package controller;

import application.Principal;
import application.RegisterPerson;
import dao.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import jdbc.exception.DbException;
import model.Person;
import util.Alerts;
import util.ManageFiles;
import util.Navigation;

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

    @FXML
    private ImageView imgPhoto;

    private String photoPath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnCancel.setOnMouseClicked((MouseEvent e) -> {
            Navigation.close(RegisterPerson.getStage());
            Navigation.openScreen(new Principal());
        });

        btnCancel.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                Navigation.close(RegisterPerson.getStage());
                Navigation.openScreen(new Principal());
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

        imgPhoto.setOnMouseClicked((MouseEvent e) -> {
            photoPath = ManageFiles.selectPhoto();
            imgPhoto.setImage(new Image("file:///" + photoPath));
        });

    }

    private void registerPerson() {
        String name = txtName.getText(),
                email = txtEmail.getText(),
                password = psfPassword.getText(),
                confPassword = psfConfirmPassword.getText();

        if(password.equals(confPassword)) {
            Person p = new Person(name, email, password, photoPath);
            PersonDao dao = new PersonDao();
            try {
                dao.add(p);
                Navigation.openScreen(new Principal());
                Alerts.showAlert("Sucesso", null, "Usuário cadastrado com sucesso", AlertType.INFORMATION);
                Navigation.close(RegisterPerson.getStage());
            } catch (DbException e) {
                String message = "Erro ao cadastrar usuário";
                message += "\n" + e.getMessage();
                Alerts.showAlert("Erro", null, message, AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Erro", null, "As senhas não coincidem!", AlertType.ERROR);
        }
    }

}
