package controller;

import application.ListCompanies;
import application.Principal;
import dao.CompanyDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import jdbc.exception.DbException;
import model.Company;
import util.Alerts;
import util.Navigation;

import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();

        btnGoBack.setOnMouseClicked((MouseEvent e) -> {
            Navigation.openScreen(new Principal());
            Navigation.close(ListCompanies.getStage());
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
            Alerts.showAlert("Erro", "", "Erro ao exibir a lista de empresas!\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
    }
}
