package service;

import controllers.AccountController;
import model.Carte;
import model.User;
import repo.CarteRepository;
import repo.UserRepository;

import javax.security.auth.login.FailedLoginException;

public class Service {

    CarteRepository carteRepository;
    UserRepository userRepository;

    public Service(CarteRepository carteRepository, UserRepository userRepository) {
        this.carteRepository = carteRepository;
        this.userRepository = userRepository;
    }

    public User login(User loggingUser) throws FailedLoginException {
        User user = userRepository.findOneByUsername(loggingUser.getUsername());
        if(user==null || !user.getPassword().equals(loggingUser.getPassword())){
            throw new FailedLoginException("Login failed");
        }
        return user;
    }

    public Iterable<Carte> getCarti() {
        return carteRepository.findAll();
    }

    public void updateCarte(Carte carte) {
        carte.setExemplare(carte.getExemplare()-1);
        carteRepository.update(carte);
    }
}
