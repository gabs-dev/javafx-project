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
    private Button btnRegister;

    @FXML
    private ImageView imgPhoto;

    private String photoPath;

    private String standardPhoto = "/resources/image/system/icon-photo.png";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        photoPath = standardPhoto;

        btnRegister.setOnMouseClicked((MouseEvent e) -> registerPerson());

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

        if (!(name.equals("")) && !(email.equals("")) && !(password.equals("")) && !(confPassword.equals(""))) {
            if (password.equals(confPassword)) {
                if (new PersonDao().findByEmail(email) == null) {
                    Person p = new Person(name, email, password, photoPath);
                    PersonDao dao = new PersonDao();
                    try {
                        dao.add(p);
                        clearFields();
                        Alerts.showAlert("Sucesso", null,
                                "Usuário cadastrado com sucesso", AlertType.INFORMATION);
                    } catch (DbException e) {
                        String message = "Erro ao cadastrar usuário";
                        message += "\n" + e.getMessage();
                        Alerts.showAlert("Erro", null, message, AlertType.ERROR);
                    }
                } else {
                    Alerts.showAlert("Erro", null, "Email já cadastrado!", AlertType.ERROR);
                }
            } else {
                Alerts.showAlert("Erro", null, "As senhas não coincidem!", AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Campos vazios", "",
                    "É necessário que todos os campos sejam preenchidos!", AlertType.WARNING);
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtEmail.setText("");
        psfPassword.setText("");
        psfConfirmPassword.setText("");
        imgPhoto.setImage(new Image("/resources/image/system/sem-foto.png"));
    }

}
