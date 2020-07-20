package controller;

import dao.PersonDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import jdbc.exception.DbException;
import model.Person;
import sample.ListPeople;
import sample.Principal;
import util.Alerts;
import util.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

public class ListPeopleController implements Initializable {

    @FXML
    private TableView<Person> table;

    @FXML
    private TableColumn<Person, Long> clmID;

    @FXML
    private TableColumn<Person, String> clmName;

    @FXML
    private TableColumn<Person, String> clmEmail;

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
            Navigation.close(ListPeople.getStage());
        });
    }

    private void initTable() {
        clmID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        table.setItems(updateTable());
    }

    private ObservableList<Person> updateTable() {
        PersonDao dao = new PersonDao();
        try {
            return FXCollections.observableArrayList(dao.findAll());
        } catch (DbException e) {
            Alerts.showAlert("Erro", "", "Erro ao exibir lista de pessoas!\n" + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
    }

}
