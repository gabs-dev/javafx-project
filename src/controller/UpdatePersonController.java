package controller;

import application.UpdatePerson;
import dao.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import jdbc.exception.DbException;
import model.Person;
import util.Alerts;
import util.ManageFiles;
import util.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdatePersonController implements Initializable {

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
    private Button btnUpdate;

    @FXML
    private ImageView imgPhoto;

    @FXML
    private TextField txtID;

    private static Person person;

    private String photoPath = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initPerson();

        btnCancel.setOnMouseClicked((MouseEvent e) -> {
            Navigation.close(UpdatePerson.getStage());
        });

        btnUpdate.setOnMouseClicked((MouseEvent e) -> {
            update();
        });

        imgPhoto.setOnMouseClicked((MouseEvent e) -> {
            photoPath = ManageFiles.selectPhoto();
            imgPhoto.setImage(new Image("file:///" + photoPath));
        });
    }

    private void initPerson() {
        if (person != null) {
            imgPhoto.setImage(new Image("file:///" + person.getPhoto()));
            txtID.setText(person.getId().toString());
            txtName.setText(person.getName());
            txtEmail.setText(person.getEmail());
            psfPassword.setText(person.getPassword());
            psfConfirmPassword.setText(person.getPassword());
            photoPath = person.getPhoto();
        } else {
            throw new NullPointerException();
        }
    }

    public static Person getPerson() {
        return person;
    }

    public static void setPerson(Person person) {
        UpdatePersonController.person = person;
    }

    private void update() {
        if (!(txtName.getText().equals("")) && !(txtEmail.getText().equals("")) && !(psfPassword.getText().equals(""))
                && !(psfConfirmPassword.getText().equals(""))) {
            if (psfPassword.getText().equals(psfConfirmPassword.getText())) {
                PersonDao dao = new PersonDao();
                Person p = new Person();
                p.setId(Long.parseLong(txtID.getText()));
                p.setName(txtName.getText());
                p.setEmail(txtEmail.getText());
                p.setPassword(psfConfirmPassword.getText());
                p.setPhoto(photoPath);
                try {
                    dao.update(p);
                    Navigation.close(UpdatePerson.getStage());
                    Alerts.showAlert("Atualizado", "", "Usuário atualizado com sucesso!",
                            AlertType.INFORMATION);
                } catch (DbException e) {
                    e.printStackTrace();
                    Alerts.showAlert("Erro", "", "Não foi possivel atualizar!\n" + e.getMessage(),
                            AlertType.ERROR);
                }
            } else {
                Alerts.showAlert("Senhas diferentes", "",
                        "As senhas não coincidem", AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Campos vazios", "",
                    "É necessário que todos os campos sejam preenchidos!", AlertType.WARNING);
        }
    }

}
