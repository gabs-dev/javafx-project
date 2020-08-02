package controller;

import application.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.Person;
import util.FxmlLoader;
import util.Navigation;

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

    @FXML
    private Circle userPhoto;

    @FXML
    private Label lblUserName;

    @FXML
    private Button btnLogout;

    @FXML
    private BorderPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userLoggedIn();
        openAddPerson();

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

        btnLogout.setOnMouseClicked((MouseEvent e) -> {
            logout();
        });

    }

    private void openAddCompany() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("RegisterCompany");
        mainPane.setCenter(view);
    }

    private void openAddPerson() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("RegisterPerson");
        mainPane.setCenter(view);
    }

    private void listCompanies() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("ListCompanies");
        mainPane.setCenter(view);
    }

    private void listPeople() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("ListPeople");
        mainPane.setCenter(view);
    }

    private void close() {
        Navigation.close(Principal.getStage());
    }

    private void userLoggedIn() {
        Person p = LoginController.getLogged();
        if (p != null) {
            if (p.getPhoto().equals("/resources/image/system/icon-photo.png")) {
                userPhoto.setFill(new ImagePattern(new Image("/resources/image/system/icon-photo.png")));
            } else {
                userPhoto.setFill(new ImagePattern(new Image("file:///" + p.getPhoto())));
            }
            //userPhoto.setFill(new ImagePattern(new Image("file:///" + p.getPhoto())));
            lblUserName.setText(p.getName());
        }
    }

    private void logout() {
        LoginController.setLogged(null);
        Navigation.close(Principal.getStage());
        Navigation.openScreen(new Login());
    }

}
