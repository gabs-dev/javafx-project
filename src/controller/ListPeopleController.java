package controller;

import application.ListPeople;
import application.Principal;
import application.UpdatePerson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.PersonDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import jdbc.exception.DbException;
import model.Company;
import model.Person;
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
    private TextField txtSearch;

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

    private ObservableList<Person> people = FXCollections.observableArrayList();

    private String standardPhoto = "/resources/image/system/icon-photo.png";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();

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

        txtSearch.setOnKeyReleased((KeyEvent e) -> {
            table.setItems(search());
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
            people = FXCollections.observableArrayList(dao.findAll());
            return people;
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

            Paragraph paragraph = new Paragraph("Relatório de Pessoas");
            paragraph.setAlignment(1);
            doc.add(paragraph);
            paragraph = new Paragraph("  ");
            doc.add(paragraph);

            PdfPTable table = new PdfPTable(3);

            PdfPCell cell1 = new PdfPCell(new Paragraph("ID"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Nome"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Email"));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);

            for(Person p : list) {
                cell1 = new PdfPCell(new Paragraph(Long.toString(p.getId())));
                cell2 = new PdfPCell(new Paragraph(p.getName()));
                cell3 = new PdfPCell(new Paragraph(p.getEmail()));

                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
            }

            doc.add(table);
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

    private ObservableList<Person> search() {
        ObservableList<Person> searchResult = FXCollections.observableArrayList();
        for (Person p : people) {
            if (p.getName().toLowerCase().contains(txtSearch.getText().toLowerCase())) {
                searchResult.add(p);
            }
        }
        return searchResult;
    }

    private void showDetails() {
        if(selected != null) {
            if (selected.getPhoto().equals(standardPhoto)) {
                imgPhoto.setImage(new Image(selected.getPhoto()));
            } else {
                imgPhoto.setImage(new Image("file:///" + selected.getPhoto()));
            }
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
