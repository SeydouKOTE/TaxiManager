package Metier;

import Metier.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DeclarationMethodes extends Serializable {
    void addPassager(Passager passager);
    void removePassager(int id);
    void updatePassager(Passager passager);
    void addConducteur(Conducteur conducteur);
    void removeConducteur(int id);
    void updateConducteur(Conducteur conducteur);
    void addPaiement(Paiement paiement);
    void removePaiement(int id);
    public Passager getPassagerById(int id);
    public Conducteur getConducteurById(int id);
    void updatePaiement(Paiement paiement);
    void addPayer(Payer payer);
    void removePayer(Long id1, Long id2, LocalDate dateDebut, LocalDate dateFin);
    void updatePayer(Payer payer);
    void addReserver(Reserver reserver);
    void removeReserver(Long id1, Long id2, LocalDate date, LocalTime heure);
    void updateReserver(Reserver reserver);
    void addPayer(Long l1,Long l2,LocalDate d1, LocalDate d2);
    List<Passager> getPassagers();
    List<Conducteur> getConducteurs();
}
