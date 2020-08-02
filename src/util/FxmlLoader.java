package util;

import application.Principal;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.URL;

public class FxmlLoader {

    private Pane view;

    public Pane getPage(String fileName) {
        try {
            URL fileUrl = Principal.class.getResource("/view/" + fileName + ".fxml");
            if (fileUrl == null) {
                throw new FileNotFoundException("FXML file can't be found");
            }
            view = new FXMLLoader().load(fileUrl);
        } catch (Exception e) {
            System.out.println("No page " + fileName + " please check FxmlLoader.");
        }
        return view;
    }

}
