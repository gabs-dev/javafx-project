package util;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ManageFiles {

    public static String selectPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.jpeg", "*.png"));
        File file = fileChooser.showOpenDialog(new Stage());
        String photoPath = "";
        if (file != null) {
            photoPath = file.getAbsolutePath();
        }
        return photoPath;
    }

}
