package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Principal;
import sample.RegisterCompany;
import sample.RegisterPerson;

import java.net.URL;
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

        });

        btnListCompanies.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {

            }
        });

        btnListPeople.setOnMouseClicked((MouseEvent e) -> {

        });

        btnListPeople.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() == KeyCode.ENTER) {

            }
        });

    }

    public void openAddCompany() {
        RegisterCompany registerCompany = new RegisterCompany();
        close();
        try {
            registerCompany.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openAddPerson() {
        RegisterPerson registerPerson = new RegisterPerson();
        close();
        try {
            registerPerson.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void close() {
        Principal.getStage().close();
    }

}
