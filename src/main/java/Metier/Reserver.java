package Metier;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Reserver")
public class Reserver implements Serializable {
    @EmbeddedId
    private CleConducteurPassager cleConducteurPassager;

    @ManyToOne
    @MapsId("identifiant_C")
    @JoinColumn(name = "identifiant_C")
    private Conducteur conducteur;

    @ManyToOne
    @MapsId("identifiant_P")
    @JoinColumn(name = "identifiant_P")
    private Passager passager;

    public Reserver() {
    }

    public Reserver(Conducteur conducteur, Passager passager) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        this.cleConducteurPassager = new CleConducteurPassager(conducteur.getIdentifiant_C(), passager.getIdendifiant_P(), currentDate, currentTime);
        this.conducteur = conducteur;
        this.passager = passager;
    }

    // Getters and setters
    public CleConducteurPassager getCleConducteurPassager() {
        return cleConducteurPassager;
    }

    public void setCleConducteurPassager(CleConducteurPassager cleConducteurPassager) {
        this.cleConducteurPassager = cleConducteurPassager;
    }

    public Conducteur getConducteur() {
        return conducteur;
    }

    public void setConducteur(Conducteur conducteur) {
        this.conducteur = conducteur;
    }

    public Passager getPassager() {
        return passager;
    }

    public void setPassager(Passager passager) {
        this.passager = passager;
    }

    public LocalDate getDate() {
        return cleConducteurPassager.getDate();
    }

    public void setDate(LocalDate date) {
        this.cleConducteurPassager.setDate(date);
    }

    public LocalTime getHeure() {
        return cleConducteurPassager.getHeure();
    }

    public void setHeure(LocalTime heure) {
        this.cleConducteurPassager.setHeure(heure);
    }
}
