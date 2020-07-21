package application;

import controller.UpdatePersonController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Person;

public class UpdatePerson extends Application {

    private static Stage stage;



    public UpdatePerson(Person p) {
        UpdatePersonController.setPerson(p);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UpdatePerson.fxml")); // carrega fxml
        Scene scene = new Scene(root); // coloca o fxml em uma cena
        scene.getStylesheets().add("/resources/css/style.css");
        stage.setTitle("Alterar dados");
        stage.setResizable(false);
        stage.setScene(scene); // coloca a cena em uma janela
        stage.show(); // abre a janela
        setStage(stage);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        UpdatePerson.stage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}