package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import service.Service;

import javax.security.auth.login.FailedLoginException;
import java.io.IOException;

public class LoginController {
    private Service service;
    private User user;

    @FXML
    TextField textFieldUser;

    @FXML
    TextField textFieldPassword;

    public void handleLogin(ActionEvent actionEvent) {
        try{
            User user = new User(textFieldUser.getText(),textFieldPassword.getText());
            this.user = service.login(user);
            if(this.user.getIsBibliotecar())
                bibliotecarAccount(this.user);
            else
                userAccount(this.user);
        }
        catch (FailedLoginException serviceException){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"ERROR",serviceException.getMessage());
        }
    }

    private void bibliotecarAccount(User user) {
        Stage loginStage = (Stage) textFieldUser.getScene().getWindow();
        loginStage.close();
        Stage stage = new Stage();
        stage.setTitle(user.getUsername());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/bibliotecar.fxml"));
        try {
            AnchorPane layout = (AnchorPane)loader.load();
            BibliotercarController bibliotercarController = (BibliotercarController) loader.getController();
            stage.setScene(new Scene(layout));
            bibliotercarController.setServiceAndUser(service,user,loginStage);
            textFieldUser.clear();
            textFieldPassword.clear();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void userAccount(User user) {
        Stage loginStage = (Stage) textFieldUser.getScene().getWindow();
        loginStage.close();
        Stage stage = new Stage();
        stage.setTitle(user.getUsername());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/abonat.fxml"));
        try {
            AnchorPane layout = (AnchorPane)loader.load();
            AccountController accountController = (AccountController) loader.getController();
            stage.setScene(new Scene(layout));
            accountController.setServiceAndUser(service,user,loginStage);
            service.addObserver(accountController,user.getId());
            textFieldUser.clear();
            textFieldPassword.clear();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setService(Service service) {
        this.service = service;
    }
}
