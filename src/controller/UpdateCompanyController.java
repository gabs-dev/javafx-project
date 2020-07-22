package controller;

import application.UpdateCompany;
import dao.CompanyDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import jdbc.exception.DbException;
import model.Company;
import util.Alerts;
import util.CpfCnpjValidator;
import util.ManageFiles;
import util.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCompanyController implements Initializable {

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCnpj;

    @FXML
    private ImageView imgPhoto;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnUpdate;

    private static Company company;

    private String photoPath = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCompany();

        btnCancel.setOnMouseClicked((MouseEvent e) -> {
            Navigation.close(UpdateCompany.getStage());
        });

        btnUpdate.setOnMouseClicked((MouseEvent e) -> {
            update();
        });

        imgPhoto.setOnMouseClicked((MouseEvent e) -> {
            photoPath = ManageFiles.selectPhoto();
            imgPhoto.setImage(new Image("file:///" + photoPath));
        });
    }

    private void initCompany() {
        if (company != null) {
            imgPhoto.setImage(new Image("file:///" + company.getPhoto()));
            txtID.setText(company.getId().toString());
            txtName.setText(company.getName());
            txtEmail.setText(company.getEmail());
            txtCnpj.setText(company.getCnpj());
            photoPath = company.getPhoto();
        } else {
            throw new NullPointerException();
        }
    }

    public static Company getCompany() {
        return company;
    }

    public static void setCompany(Company c) {
        company = c;
    }

    private void update() {
        Long id = Long.parseLong(txtID.getText());
        String name = txtName.getText(),
                email = txtEmail.getText(),
                cnpj = txtCnpj.getText();
        if (!(name.equals("")) && !(email.equals("")) && !(cnpj.equals(""))) {
            if (CpfCnpjValidator.isCnpj(cnpj)) {
                CompanyDao dao = new CompanyDao();
                Company c = new Company(id, name, email, cnpj, photoPath);
                try {
                    dao.update(c);
                    Navigation.close(UpdateCompany.getStage());
                    Alerts.showAlert("Atualizada", "", "Empresa atualizada com sucesso!",
                            AlertType.INFORMATION);
                } catch (DbException e) {
                    e.printStackTrace();
                    Alerts.showAlert("Erro", "", "Não foi possivel atualizar!\n" + e.getMessage(),
                            AlertType.ERROR);
                }
            } else {
                Alerts.showAlert("CNPJ inválido", "",
                        "Insira um CNPJ válido!", AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Campos vazios", "",
                    "É necessário que todos os campos sejam preenchidos!", AlertType.WARNING);
        }
    }
    
}
