package controller;

import application.ListCompanies;
import application.Principal;
import dao.CompanyDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import jdbc.exception.DbException;
import model.Company;
import util.Alerts;
import util.Navigation;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListCompaniesController implements Initializable {

    @FXML
    private TableView<Company> table;

    @FXML
    private TableColumn<Company, Long> clmID;

    @FXML
    private TableColumn<Company, String> clmName;

    @FXML
    private TableColumn<Company, String> clmEmail;

    @FXML
    private TableColumn<Company, String> clmCNPJ;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnGoBack;

    @FXML
    private ImageView imgPhoto;

    @FXML
    private Label lblID;

    @FXML
    private Label lblName;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblCNPJ;

    private Company selected;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();

        btnGoBack.setOnMouseClicked((MouseEvent e) -> {
            Navigation.openScreen(new Principal());
            Navigation.close(ListCompanies.getStage());
        });

        btnDelete.setOnMouseClicked((MouseEvent e) -> {
            delete();
        });

        btnUpdate.setOnMouseClicked((MouseEvent e) -> {
            table.setItems(updateTable());
        });

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Company>() {
            @Override
            public void changed(ObservableValue<? extends Company> observableValue, Company oldValue, Company newValue) {
                selected = newValue;
                showDetails();
            }
        });
    }

    private void initTable() {
        clmID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        table.setItems(updateTable());
    }

    private ObservableList<Company> updateTable() {
        CompanyDao dao = new CompanyDao();
        try {
            return FXCollections.observableArrayList(dao.findAll());
        } catch (DbException e) {
            Alerts.showAlert("Erro", "",
                    "Erro ao exibir a lista de empresas!\n" + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
    }

    private void delete() {
        CompanyDao dao = new CompanyDao();
        if (selected != null) {
            try {
                Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
                        "Tem certeza que deseja excluir a empresa " + selected.getName() + "?");
                if (result.get() == ButtonType.OK) {
                    dao.delete(selected);
                    Alerts.showAlert("Excluída",  null,
                            "Empresa excluída com sucesso!", AlertType.INFORMATION);
                    table.setItems(updateTable());
                }
            } catch (DbException e) {
                e.printStackTrace();
                Alerts.showAlert("Erro", null,
                        "Não foi possível excluir a empresa!\n" + e.getMessage(), AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Selecione uma empresa", null,
                    "É preciso selecionar uma empresa para excluir!", AlertType.WARNING);
        }
    }

    private void showDetails() {
        if(selected != null) {
            imgPhoto.setImage(new Image("file:///" + selected.getPhoto()));
            lblID.setText("ID: " + selected.getId());
            lblName.setText("Name: " + selected.getName());
            lblEmail.setText("Email: " + selected.getEmail());
            lblCNPJ.setText("CNPJ: " + selected.getCnpj());
        } else {
            imgPhoto.setImage(new Image("file:///"));
            lblID.setText("");
            lblName.setText("");
            lblEmail.setText("");
            lblCNPJ.setText("");
        }
    }

}
