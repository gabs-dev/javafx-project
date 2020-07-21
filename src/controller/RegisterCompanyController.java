package controller;

import application.Principal;
import application.RegisterCompany;
import application.RegisterPerson;
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
            registerCompany();
        });

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

        if (CpfCnpjValidator.isCnpj(cnpj)) {
            Company c = new Company(name, email, cnpj, photoPath);
            CompanyDao dao = new CompanyDao();
            try {
                dao.add(c);
                Navigation.openScreen(new Principal());
                Alerts.showAlert("Sucesso", null, "Empresa cadastrada com sucesso", AlertType.INFORMATION);
                Navigation.close(RegisterCompany.getStage());
            } catch (DbException e) {
                String message = "Erro ao cadastrar empresa";
                message += "\n" + e.getMessage();
                Alerts.showAlert("Erro", null, message, AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Erro", null, "CNPJ inválido", AlertType.ERROR);
        }
    }

}
