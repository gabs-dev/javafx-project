package controller;

import dao.CompanyDao;
import dao.PersonDao;
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
import model.Person;
import sample.ListPeople;
import sample.Principal;
import sample.RegisterCompany;
import sample.RegisterPerson;
import util.Alerts;

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
        RegisterCompany registerCompany = new RegisterCompany();
        close();
        try {
            registerCompany.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAddPerson() {
        RegisterPerson registerPerson = new RegisterPerson();
        close();
        try {
            registerPerson.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listCompanies() {
        try {
            CompanyDao dao = new CompanyDao();
            StringBuilder sb = new StringBuilder();
            sb.append("LISTANDO EMPRESAS\n");
            List<Company> list = dao.findAll();
            for (Company c : list) {
                sb.append(c);
                sb.append("\n------------------------\n");
            }
            System.out.println(sb.toString());
        } catch (DbException e) {
            String message = "Não foi possível listar as empresas";
            message += "\n" + e.getMessage();
            Alerts.showAlert("Erro", null, message, AlertType.ERROR);
        }
    }

    private void listPeople() {
        ListPeople screen = new ListPeople();
        try {
            close();
            screen.start(new Stage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void close() {
        Principal.getStage().close();
    }

}
