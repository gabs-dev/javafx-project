package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Principal extends Application {

    private static Stage stage;
    private static String cssFile = "/resources/css/style.css";

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Principal.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssFile);
        stage.setTitle("Main");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        setStage(stage);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Principal.stage = stage;
    }

    public static void switchScene(String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(Principal.class.getResource(fxmlFile));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssFile);
        stage.setScene(scene);
        stage.show();
        setStage(stage);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
