package Metier;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "Payer")
public class Payer implements Serializable {
    @EmbeddedId
    private CleConducteurPaiement cleConducteurPaiement;

    @ManyToOne
    @MapsId("identifiant_C")
    @JoinColumn(name = "identifiant_C")
    private Conducteur conducteur;

    @ManyToOne
    @MapsId("id_paiement")
    @JoinColumn(name = "id_paiement")
    private Paiement paiement;

    public Payer() {
    }

    public Payer(Conducteur conducteur, Paiement paiement) {
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.plus(Period.ofDays(Math.toIntExact(paiement.getId_paiement())));
        this.cleConducteurPaiement = new CleConducteurPaiement(conducteur.getIdentifiant_C(), paiement.getId_paiement(), currentDate, endDate);
        this.conducteur = conducteur;
        this.paiement = paiement;
    }

    // Getters and setters
    public CleConducteurPaiement getCleConducteurPaiement() {
        return cleConducteurPaiement;
    }

    public void setCleConducteurPaiement(CleConducteurPaiement cleConducteurPaiement) {
        this.cleConducteurPaiement = cleConducteurPaiement;
    }

    public Conducteur getConducteur() {
        return conducteur;
    }

    public void setConducteur(Conducteur conducteur) {
        this.conducteur = conducteur;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public LocalDate getDateDebut() {
        return cleConducteurPaiement.getDateDebut();
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.cleConducteurPaiement.setDateDebut(dateDebut);
    }

    public LocalDate getDateFin() {
        return cleConducteurPaiement.getDateFin();
    }

    public void setDateFin(LocalDate dateFin) {
        this.cleConducteurPaiement.setDateFin(dateFin);
    }
}
