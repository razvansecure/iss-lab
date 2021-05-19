package controllers;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {
    static void showMessage(Stage owner,Alert.AlertType type,String header,String text){
        Alert alert = new Alert(type);
        alert.initOwner(owner);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
