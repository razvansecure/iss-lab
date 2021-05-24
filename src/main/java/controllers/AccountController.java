package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Carte;
import model.User;
import service.IObserver;
import service.Service;

import java.io.Serializable;
import java.util.Collection;

public class AccountController implements IObserver {
    private Service service;
    private User user;
    private Stage loginStage;
    private ObservableList<Carte> model = FXCollections.observableArrayList();

    @FXML
    TableView<Carte> tableView;

    @FXML
    TableColumn<Carte, String> tableColumnTitlu;

    @FXML
    TableColumn<Carte, String> tableColumnAutor;

    @FXML
    TableColumn<Carte, Integer> tableColumnNrExemplare;

    @FXML
    public void initialize(){
        tableColumnTitlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        tableColumnAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tableColumnNrExemplare.setCellValueFactory(new PropertyValueFactory<>("exemplare"));
        tableView.setItems(model);
    }

    public void setServiceAndUser(Service service, User user, Stage loginStage) {
        model.clear();
        this.service = service;
        this.user = user;
        this.loginStage = loginStage;
        model.addAll((Collection<? extends Carte>) service.getCarti());
    }

    public void handleImprumuta(ActionEvent actionEvent) {
        Carte carte = tableView.getSelectionModel().getSelectedItem();
        if(carte == null){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "error" , "Trebuie sa selectati o carte");
            return;
        }
        service.imprumuta(carte,user);
        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "succes" , "Ati imprumutat cartile. Trebuie sa le returnati in maxim 10 zile dupa care se adauga o taxa de penalizare");
        model.clear();
        model.addAll((Collection<? extends Carte>) service.getCarti());
    }

    @Override
    public void carteChanged(Carte updatedCarte) {
        for(Carte carte : model){
            if(carte.getId().equals(updatedCarte.getId())){
                model.remove(carte);
                if(updatedCarte.getExemplare()>0)
                    model.add(updatedCarte);
                return;
            }
        }
    }
}
