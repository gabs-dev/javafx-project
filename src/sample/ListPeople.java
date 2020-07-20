package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ListPeople extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ListPeople.fxml")); // carrega fxml
        Scene scene = new Scene(root); // coloca o fxml em uma cena
        scene.getStylesheets().add("/resources/css/style.css");
        stage.setTitle("Lista de pessoas");
        stage.setResizable(false);
        stage.setScene(scene); // coloca a cena em uma janela
        stage.show(); // abre a janela
        setStage(stage);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ListPeople.stage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
