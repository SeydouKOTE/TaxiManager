package Metier;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Embeddable
public class CleConducteurPassager implements Serializable {
    private Long identifiant_C;
    private Long identifiant_P;
    private LocalDate date;
    private LocalTime heure;

    public CleConducteurPassager() {
    }

    public CleConducteurPassager(Long identifiant_C, Long identifiant_P, LocalDate date, LocalTime heure) {
        this.identifiant_C = identifiant_C;
        this.identifiant_P = identifiant_P;
        this.date = date;
        this.heure = heure;
    }

    // Getters and setters
    public Long getIdentifiant_C() {
        return identifiant_C;
    }

    public void setIdentifiant_C(Long identifiant_C) {
        this.identifiant_C = identifiant_C;
    }

    public Long getIdentifiant_P() {
        return identifiant_P;
    }

    public void setIdentifiant_P(Long identifiant_P) {
        this.identifiant_P = identifiant_P;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    // hashCode and equals methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CleConducteurPassager that = (CleConducteurPassager) o;
        return Objects.equals(identifiant_C, that.identifiant_C) &&
                Objects.equals(identifiant_P, that.identifiant_P) &&
                Objects.equals(date, that.date) &&
                Objects.equals(heure, that.heure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiant_C, identifiant_P, date, heure);
    }
}
