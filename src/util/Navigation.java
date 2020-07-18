package util;

import javafx.stage.Stage;
import sample.Principal;

public class Navigation {

    public static void openPrincipal() {
        Principal p = new Principal();
        try {
            p.start(new Stage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
}
