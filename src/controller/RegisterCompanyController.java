package controller;

import application.Principal;
import application.RegisterCompany;
import dao.CompanyDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import jdbc.exception.DbException;
import model.Company;
import util.Alerts;
import util.CpfCnpjValidator;
import util.ManageFiles;
import util.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterCompanyController implements Initializable {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCnpj;

    @FXML
    private Button btnRegister;

    @FXML
    private ImageView imgPhoto;

    private String photoPath;

    private String standardPhoto = "/resources/image/system/company.png";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        photoPath = standardPhoto;

        btnRegister.setOnMouseClicked((MouseEvent e) -> registerCompany());

        btnRegister.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                registerCompany();
            }
        });

        imgPhoto.setOnMouseClicked((MouseEvent e) -> {
            photoPath = ManageFiles.selectPhoto();
            imgPhoto.setImage(new Image("file:///" + photoPath));
        });

    }

    private void registerCompany() {
        String name = txtName.getText(),
                email = txtEmail.getText(),
                cnpj = txtCnpj.getText();

        if (!(name.equals("")) && !(email.equals("")) && !(cnpj.equals(""))) {
            if (CpfCnpjValidator.isCnpj(cnpj)) {
                Company c = new Company(name, email, cnpj, photoPath);
                CompanyDao dao = new CompanyDao();
                try {
                    dao.add(c);
                    clearFields();
                    Alerts.showAlert("Sucesso", null, "Empresa cadastrada com sucesso", AlertType.INFORMATION);
                } catch (DbException e) {
                    String message = "Erro ao cadastrar empresa";
                    message += "\n" + e.getMessage();
                    Alerts.showAlert("Erro", null, message, AlertType.ERROR);
                }
            } else {
                Alerts.showAlert("Erro", null, "CNPJ inválido", AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Campos vazios", "",
                    "É necessário que todos os campos sejam preenchidos!", AlertType.WARNING);
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtEmail.setText("");
        txtCnpj.setText("");
        imgPhoto.setImage(new Image("/resources/image/system/sem-foto.png"));
    }

}
