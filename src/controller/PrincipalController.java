package controller;

import application.*;
import dao.CompanyDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jdbc.exception.DbException;
import model.Company;
import util.Alerts;
import util.Navigation;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    @FXML
    private Button btnAddPerson;

    @FXML
    private Button btnAddCompany;

    @FXML
    private Button btnListPeople;

    @FXML
    private Button btnListCompanies;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnAddCompany.setOnMouseClicked((MouseEvent e) -> {
            openAddCompany();
        });

        btnAddCompany.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                openAddCompany();
            }
        });

        btnAddPerson.setOnMouseClicked((MouseEvent e) -> {
            openAddPerson();
        });

        btnAddPerson.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                openAddPerson();
            }
        });

        btnListCompanies.setOnMouseClicked((MouseEvent e) -> {
            listCompanies();
        });

        btnListCompanies.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                listCompanies();
            }
        });

        btnListPeople.setOnMouseClicked((MouseEvent e) -> {
            listPeople();
        });

        btnListPeople.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                listPeople();
            }
        });

    }

    private void openAddCompany() {
        close();
        Navigation.openScreen(new RegisterCompany());
    }

    private void openAddPerson() {
        close();
        Navigation.openScreen(new RegisterPerson());
    }

    private void listCompanies() {
        Navigation.openScreen(new ListCompanies());
        close();
    }

    private void listPeople() {
        Navigation.openScreen(new ListPeople());
        close();
    }

    private void close() {
        Navigation.close(Principal.getStage());
    }

}
