package jdbc;

import javafx.scene.control.Alert.AlertType;
import view.util.Alerts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection getConnection() {
        try {
            String user = "postgres";
            String password = "1234567";
            String databaseName = "javafx-project";
            String serverAddress = "localhost";

            return DriverManager.getConnection("jdbc:postgresql://" + serverAddress +
                    "/" + databaseName, user, password);
        } catch (SQLException e) {
            String message = "Falha ao realizar conexão com o banco de dados!";
            //Alerts.showAlert("Erro", null, message, AlertType.ERROR);
            System.err.println(message);
            throw  new RuntimeException(e);
        }
    }

}
