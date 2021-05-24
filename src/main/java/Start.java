import repo.CarteRepository;
import repo.ImprumutRepository;
import repo.UserRepository;
import service.Service;

public class Start {
    private static Service service = null;
    public static void main(String[] args) {
        Main.main(args);
    }

    public static Service getService() {
        if(service == null) {
            UserRepository.initialize();
            CarteRepository.initialize();
            ImprumutRepository.initialize();
            UserRepository userRepository = new UserRepository();
            CarteRepository carteRepository = new CarteRepository();
            ImprumutRepository imprumutRepository = new ImprumutRepository();
            service = new Service(carteRepository, userRepository, imprumutRepository);
        }
        return service;
    }
}
