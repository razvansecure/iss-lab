package service;

import model.Carte;

public interface IObserver {
    void carteChanged(Carte updatedCarte);
}
