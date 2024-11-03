package Metier.Serveur;

import java.io.Serializable;

public class Profile implements Serializable {
    private String nom;
    private String status;

    public Profile(String nom, String status) {
        this.nom = nom;
        this.status = status;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "nom='" + nom + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
