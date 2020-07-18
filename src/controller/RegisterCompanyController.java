package controller;

import dao.CompanyDao;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jdbc.exception.DbException;
import model.Company;
import sample.Principal;
import sample.RegisterCompany;
import util.Alerts;
import util.CpfCnpjValidator;

import java.net.URL;
import java.util.InputMismatchException;
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
            registerCompany();
        });

        btnRegister.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                registerCompany();
            }
        });

    }

    private void registerCompany() {
        String name = txtName.getText(),
                email = txtEmail.getText(),
                cnpj = txtCnpj.getText();

        if (CpfCnpjValidator.isCnpj(cnpj)) {
            Company c = new Company(name, email, cnpj);
            CompanyDao dao = new CompanyDao();
            try {
                dao.add(c);
                openPrincipal();
                Alerts.showAlert("Sucesso", null, "Empresa cadastrada com sucesso", AlertType.INFORMATION);
                close();
            } catch (DbException e) {
                String message = "Erro ao cadastrar empresa";
                message += "\n" + e.getMessage();
                Alerts.showAlert("Erro", null, message, AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Erro", null, "CNPJ inválido", AlertType.ERROR);
        }
    }

    public void close() {
        RegisterCompany.getStage().close();
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
