import controllers.AccountController;
import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Carte;
import repo.CarteRepository;
import repo.ImprumutRepository;
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
        ImprumutRepository.initialize();
        UserRepository userRepository = new UserRepository();
        CarteRepository carteRepository = new CarteRepository();
        ImprumutRepository imprumutRepository = new ImprumutRepository();
        Service service = new Service(carteRepository,userRepository,imprumutRepository);
        for(int i=0;i<3;i++) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/login.fxml"));
            AnchorPane layout = (AnchorPane) loader.load();
            stage.setScene(new Scene(layout));
            stage.setTitle("Login");
            LoginController loginController = (LoginController) loader.getController();
            loginController.setService(service);
            stage.show();
        }
    }
}
