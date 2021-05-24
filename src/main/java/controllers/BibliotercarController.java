package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Carte;
import model.Imprumut;
import model.User;
import service.IObserver;
import service.Service;

import java.util.Collection;

public class BibliotercarController{
    private Service service;
    private User user;
    private Stage loginStage;
    private ObservableList<User> modelUser = FXCollections.observableArrayList();
    private ObservableList<Imprumut> modelImprumut = FXCollections.observableArrayList();

    @FXML
    TableView<User> tableViewUsers;

    @FXML
    TableView<Imprumut> tableViewImprumut;

    @FXML
    TableColumn<User, String> tableColumnNume;

    @FXML
    TableColumn<User, String> tableColumnCNP;

    @FXML
    TableColumn<User, String> tableColumnAdresa;

    @FXML
    TableColumn<User, String> tableColumnTelefon;

    @FXML
    TableColumn<Imprumut, String> tableColumnTitlu;

    @FXML
    TableColumn<Imprumut, String> tableColumnAutor;

    @FXML
    TableColumn<Imprumut, String> tableColumnDataImprumut;

    @FXML
    TextField textFieldTitlu;

    @FXML
    TextField textFieldAutor;

    @FXML
    TextField textFieldExemplare;

    @FXML
    public void initialize(){
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tableColumnCNP.setCellValueFactory(new PropertyValueFactory<>("cnp"));
        tableColumnAdresa.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        tableColumnTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        tableColumnTitlu.setCellValueFactory(x-> new SimpleStringProperty(service.findCarte(x.getValue().getCarte()).getTitlu()));
        tableColumnAutor.setCellValueFactory(x-> new SimpleStringProperty(service.findCarte(x.getValue().getCarte()).getAutor()));
        tableColumnDataImprumut.setCellValueFactory(new PropertyValueFactory<>("dataImprumut"));
        tableViewUsers.setItems(modelUser);
        tableViewImprumut.setItems(modelImprumut);
    }

    public void setServiceAndUser(Service service, User user, Stage loginStage) {
        modelUser.clear();
        modelImprumut.clear();
        this.service = service;
        this.user = user;
        this.loginStage = loginStage;
        modelUser.addAll((Collection<? extends User>) service.getAbonati());
    }

    public void returnareCarti(ActionEvent actionEvent) {
        Imprumut imprumut = tableViewImprumut.getSelectionModel().getSelectedItem();
        if(imprumut == null){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"ERROR","Trebuie sa selectati un imprumut");
            return;
        }
        boolean taxa = service.returnareImprumut(imprumut);
        if(taxa){
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"TAXA","Trebuie sa achitati taxa pentru intarziere de peste 10 zile!");
        }
        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"RETURNARE","Cartea a fost returnata!");
        modelImprumut.remove(imprumut);
    }

    public void showImprumuturi(ActionEvent actionEvent) {
        User user = tableViewUsers.getSelectionModel().getSelectedItem();
        if(user == null){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"ERROR","Trebuie sa selectati un user");
            return;
        }
        modelImprumut.clear();
        modelImprumut.addAll((Collection<? extends Imprumut>) service.getImprumuturiUser(user));
    }

    public void handleAddCarte(ActionEvent actionEvent) {
        if(textFieldTitlu.getText().isEmpty() || textFieldAutor.getText().isEmpty()){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"ERROR","Trebuie sa completati titlul si autorul");
        }
        int x;
        try {
            x = Integer.parseInt(textFieldExemplare.getText());
            if(x<1)
                throw new Exception();
        }
        catch(Exception e){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"ERROR","Numarul de exemplare trebuie sa fie un intreg mai mare ca 0!");
            return;
        }
        service.addCarte(textFieldTitlu.getText(), textFieldAutor.getText(), x);

    }
}
