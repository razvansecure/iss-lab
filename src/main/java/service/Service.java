package service;

import controllers.AccountController;
import model.Carte;
import model.Imprumut;
import model.User;
import repo.CarteRepository;
import repo.ImprumutRepository;
import repo.UserRepository;

import javax.security.auth.login.FailedLoginException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service {

    Map<Long, IObserver> loggedClients;
    CarteRepository carteRepository;
    UserRepository userRepository;
    ImprumutRepository imprumutRepository;

    public Service(CarteRepository carteRepository, UserRepository userRepository, ImprumutRepository imprumutRepository) {
        this.carteRepository = carteRepository;
        this.userRepository = userRepository;
        this.imprumutRepository = imprumutRepository;
        this.loggedClients = new ConcurrentHashMap<>();
    }

    public User login(User loggingUser) throws FailedLoginException {
        User user = userRepository.findOneByUsername(loggingUser.getUsername());
        if(user==null || !user.getPassword().equals(loggingUser.getPassword())){
            throw new FailedLoginException("Login failed");
        }
        if(loggedClients.containsKey(user.getId())){
            throw new FailedLoginException("User already logged in");
        }
        return user;
    }

    public Iterable<Carte> getCarti() {
        return carteRepository.findAll();
    }

    public synchronized void imprumuta(Carte carte, User user) {
        carte.setExemplare(carte.getExemplare()-1);
        imprumutRepository.save(new Imprumut(user.getId(),carte.getId(), LocalDate.now(),null));
        carteRepository.update(carte);
        notifyLoggedUsers(carte);
    }

    public Carte findCarte(long carte) {
        return carteRepository.findOne(carte);
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public Iterable<Imprumut> getImprumuturiUser(User user) {
        List<Imprumut> imprumutList = new Vector<>();
        for(Imprumut el : imprumutRepository.findAll()){
            if(el.getAbonat() == user.getId() && el.getDataReturnare() == null){
                imprumutList.add(el);
            }
        }
        return imprumutList;
    }

    public Iterable<User> getAbonati() {
        List<User> userList = new Vector<>();
        for(User el : userRepository.findAll()){
            if(!el.getIsBibliotecar())
                userList.add(el);
        }
        return userList;
    }

    public synchronized boolean returnareImprumut(Imprumut imprumut) {
        imprumut.setDataReturnare(LocalDate.now());
        imprumutRepository.update(imprumut);
        Carte carte = carteRepository.findOne(imprumut.getCarte());
        carte.setExemplare(carte.getExemplare()+1);
        carteRepository.update(carte);
        notifyLoggedUsers(carte);
        return imprumut.getDataReturnare().minusDays(10).isAfter(imprumut.getDataImprumut());
    }

    public synchronized void addObserver(IObserver observer, Long userId) {
        loggedClients.put(userId, observer);
        System.out.println(loggedClients);
    }

    private void notifyLoggedUsers(Carte carte) {
        int defaultThreadsNr = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(defaultThreadsNr);
        for(IObserver client : loggedClients.values()){
            if(client != null){
                executorService.execute(()->
                {
                    client.carteChanged(carte);
                });
            }
        }

    }

    public void addCarte(String titlu, String autor, int exemplare) {
        carteRepository.save(new Carte(titlu,autor,exemplare));
    }
}
