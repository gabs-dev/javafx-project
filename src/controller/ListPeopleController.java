package controller;

import dao.PersonDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import jdbc.exception.DbException;
import model.Person;
import application.ListPeople;
import application.Principal;
import util.Alerts;
import util.Navigation;

import java.net.URL;
import java.util.Optional;
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

    private Person selected;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();

        btnGoBack.setOnMouseClicked((MouseEvent e) -> {
            Navigation.openScreen(new Principal());
            Navigation.close(ListPeople.getStage());
        });

        btnDelete.setOnMouseClicked((MouseEvent e) -> {
            delete();
        });

        btnUpdate.setOnMouseClicked((MouseEvent e) -> {
            table.setItems(updateTable());
        });

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observableValue, Person oldValue, Person newValue) {
                selected = newValue;
            }
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
            Alerts.showAlert("Erro", "",
                    "Erro ao exibir a lista de pessoas!\n" + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
    }

    private void delete() {
        PersonDao dao = new PersonDao();
        if (selected != null) {
            try {
                Optional<ButtonType> result = Alerts.showConfirmation(
                        "Confirmação", "Tem certeza que deseja excluir o(a) usuário(a) " + selected.getName() + "?");
                if (result.get() == ButtonType.OK) {
                    dao.delete(selected);
                    Alerts.showAlert("Excluído",  null,
                            "Usuário excluído com sucesso!", AlertType.INFORMATION);
                    table.setItems(updateTable());
                }
            } catch (DbException e) {
                e.printStackTrace();
                Alerts.showAlert("Erro", null,
                        "Não foi possível excluir a pessoa!\n" + e.getMessage(), AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Selecione uma pessoa", null,
                    "É preciso selecionar uma pessoa para excluir!", AlertType.WARNING);
        }
    }

}
