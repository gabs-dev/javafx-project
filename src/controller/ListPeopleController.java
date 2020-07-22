package controller;

import application.UpdatePerson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import dao.CompanyDao;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import jdbc.exception.DbException;
import model.Company;
import model.Person;
import application.ListPeople;
import application.Principal;
import util.Alerts;
import util.ManageFiles;
import util.Navigation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
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
    private Button btnGeneratePDF;

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
    private Button btnEdit;

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

        btnEdit.setOnMouseClicked((MouseEvent e) -> {
            edit();
        });

        btnGeneratePDF.setOnMouseClicked((MouseEvent e) -> {
            generatePDF();
        });

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observableValue, Person oldValue, Person newValue) {
                selected = newValue;
                showDetails();
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
                Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
                        "Tem certeza que deseja excluir o(a) usuário(a) " + selected.getName() + "?");
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

    private void edit() {
        if(selected != null) {
            Navigation.openScreen(new UpdatePerson(selected));
        } else {
            Alerts.showAlert("Selecione uma pessoa", null,
                    "É preciso selecionar uma pessoa para editar!", AlertType.WARNING);
        }
    }

    private void generatePDF() {
        Document doc = new Document();
        try {
            String folderPath = ManageFiles.chooseFolder();
            PdfWriter.getInstance(doc, new FileOutputStream(folderPath));
            doc.open();
            List<Person> list = new PersonDao().findAll();
            for(Person p : list) {
                doc.add(new Paragraph("ID: " + p.getId()));
                doc.add(new Paragraph("Nome: " + p.getName()));
                doc.add(new Paragraph("Email: " + p.getEmail()));
                doc.add(new Paragraph("Caminho da foto: " + p.getPhoto()));
                doc.add(new Paragraph("                          "));
            }
            doc.close();
            Alerts.showAlert("PDF gerado", null, "PDF gerado com sucesso!", AlertType.INFORMATION);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Alerts.showAlert("Erro", null,
                    "É necessário escolher uma pasta para salvar o arquivo", AlertType.WARNING);
        }
    }

    private void showDetails() {
        if(selected != null) {
            imgPhoto.setImage(new Image("file:///" + selected.getPhoto()));
            lblID.setText("ID: " + selected.getId());
            lblName.setText("Nome: " + selected.getName());
            lblEmail.setText("Email: " + selected.getEmail());
        } else {
            imgPhoto.setImage(new Image("file:///"));
            lblID.setText("");
            lblName.setText("");
            lblEmail.setText("");
        }
    }

}
