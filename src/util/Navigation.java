package util;

import javafx.application.Application;
import javafx.stage.Stage;
public class Navigation {

    public static <T extends Application> void openScreen(T t) {
        try {
            t.start(new Stage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void close(Stage stage) {
        stage.close();
    }
    
}
