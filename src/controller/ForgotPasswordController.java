package controller;

import application.ForgotPassword;
import dao.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.*;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import jdbc.exception.DbException;
import model.Person;
import util.*;

import javax.mail.MessagingException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnSendCode;

    @FXML
    private TextField txtCode;

    @FXML
    private Button btnValidateCode;

    @FXML
    private PasswordField psfPassword;

    @FXML
    private PasswordField psfConfirmPassword;

    @FXML
    private Button btnChangePassword;

    @FXML
    private Button btnCancel;

    private Person user = null;
    private long code;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        psfPassword.setDisable(true);
        psfConfirmPassword.setDisable(true);
        txtCode.setDisable(true);
        btnValidateCode.setDisable(true);
        btnChangePassword.setDisable(true);

        Constraints.setTextFieldInteger(txtCode);
        Constraints.setTextFieldMaxLength(txtCode, 6);

        btnSendCode.setOnMouseClicked((MouseEvent e) -> {
            sendEmail();
        });

        btnValidateCode.setOnMouseClicked((MouseEvent e) -> {
            validateCode();
        });

        btnChangePassword.setOnMouseClicked((MouseEvent e) -> {
            changePassword();
        });

        btnCancel.setOnMouseClicked((MouseEvent e) -> {
            Navigation.close(ForgotPassword.getStage());
        });
    }

    private void sendEmail() {
        if (!(txtEmail.getText().equals(""))) {
            user = new PersonDao().findByEmail(txtEmail.getText());
            if (user != null) {
                try {
                    code = CodeGenerator.generateCode();
                    String subject = "Alterar senha (não responda)";
                    String message = "Olá " + user.getName() + "!\n Aqui está o seu código de verificação: " + code
                            + ". Basta inseri-lo no campo adequado e logo em seguida validar."
                            + "\nFoi um prazer ajudá-lo!"
                            + "\n\nAtt, Company Tests.";
                    Email.sendEmail(subject, message, user.getEmail());
                    Alerts.showAlert("Email enviado", null,
                            "Email enviado com sucesso! Caso não apareça na caixa de entrada principal verifique" +
                                    " o spam.", AlertType.INFORMATION);
                    txtCode.setDisable(false);
                    btnValidateCode.setDisable(false);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    Alerts.showAlert("Erro", null,
                            "Falha ao enviar o email!\n" + e.getMessage(), AlertType.ERROR);
                }
            } else {
                Alerts.showAlert("Erro", null,
                        "Nenhum usuário foi encontrado com este email", AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Campo de email vazio!", null,
                    "Preencha o campo de email", AlertType.WARNING);
        }
    }

    private void validateCode() {
        if (!(txtCode.getText().equals(""))) {
            long cd = Long.parseLong(txtCode.getText());
            if (cd == code) {
                psfPassword.setDisable(false);
                psfConfirmPassword.setDisable(false);
                btnChangePassword.setDisable(false);
            } else {
                Alerts.showAlert("Erro", null,
                        "Código inválido! Digite novamente.", AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Campo do código vazio!", null,
                    "Preencha o campo do código", AlertType.WARNING);
        }
    }

    private void changePassword() {
        String password = psfPassword.getText();
        String confirmationPassword = psfConfirmPassword.getText();

        if (!(password.equals("")) && !(confirmationPassword.equals(""))) {
            if (password.equals(confirmationPassword)) {
                user.setPassword(password);
                try {
                    new PersonDao().update(user);
                    Alerts.showAlert("Senha alterada", null,
                            "Sua senha foi alterada com sucesso!", AlertType.INFORMATION);
                    String subject = "Senha alterada";
                    String message = "Caro usuário, informamos que a sua senha foi alterada!";
                    Email.sendEmail(subject, message, user.getEmail());
                    Navigation.close(ForgotPassword.getStage());
                } catch (DbException e) {
                    e.printStackTrace();
                    Alerts.showAlert("Erro", null,
                            "Não foi possível alterar a senha!", AlertType.ERROR);
                } catch (MessagingException e1) {
                    e1.printStackTrace();
                }
            } else {
                Alerts.showAlert("Erro", null,
                        "As senhas não coincidem!", AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Campo(s) de senha vazio(s)!", null,
                    "Preencha o(s) campo(s) de senha", AlertType.WARNING);
        }
    }

}
