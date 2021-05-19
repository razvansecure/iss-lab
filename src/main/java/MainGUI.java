import controllers.AccountController;
import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Carte;
import repo.CarteRepository;
import repo.UserRepository;
import service.Service;

public class MainGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        UserRepository.initialize();
        CarteRepository.initialize();
        UserRepository userRepository = new UserRepository();
        CarteRepository carteRepository = new CarteRepository();
        Service service = new Service(carteRepository,userRepository);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/login.fxml"));
        AnchorPane layout = (AnchorPane)loader.load();
        primaryStage.setScene(new Scene(layout));
        primaryStage.setTitle("Login");
        LoginController loginController = (LoginController)loader.getController();
        loginController.setService(service);
        primaryStage.show();
    }
}
