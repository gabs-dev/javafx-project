package application;

import controller.UpdatePersonController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Person;

public class Credits extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Credits.fxml")); // carrega fxml
        Scene scene = new Scene(root); // coloca o fxml em uma cena
        scene.getStylesheets().add("/resources/css/style.css");
        stage.setTitle("Créditos");
        stage.setResizable(false);
        stage.setScene(scene); // coloca a cena em uma janela
        stage.show(); // abre a janela
        setStage(stage);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Credits.stage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
