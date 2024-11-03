package Metier;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class CleConducteurPaiement implements Serializable {
    private Long identifiant_C;
    private Long id_paiement;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public CleConducteurPaiement() {
    }

    public CleConducteurPaiement(Long identifiant_C, Long id_paiement, LocalDate dateDebut, LocalDate dateFin) {
        this.identifiant_C = identifiant_C;
        this.id_paiement = id_paiement;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters and setters
    public Long getIdentifiant_C() {
        return identifiant_C;
    }

    public void setIdentifiant_C(Long identifiant_C) {
        this.identifiant_C = identifiant_C;
    }

    public Long getId_paiement() {
        return id_paiement;
    }

    public void setId_paiement(Long id_paiement) {
        this.id_paiement = id_paiement;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    // hashCode and equals methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CleConducteurPaiement that = (CleConducteurPaiement) o;
        return Objects.equals(identifiant_C, that.identifiant_C) &&
                Objects.equals(id_paiement, that.id_paiement) &&
                Objects.equals(dateDebut, that.dateDebut) &&
                Objects.equals(dateFin, that.dateFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiant_C, id_paiement, dateDebut, dateFin);
    }
}
