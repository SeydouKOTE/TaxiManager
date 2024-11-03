package Metier;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="Conducteur")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typeConduceur", discriminatorType = DiscriminatorType.STRING)
public class Conducteur implements Serializable {
    @Id
    private Long identifiant_C;
    @Column(name="Nom")
    private String Nom;
    @Column(name="Prenom")
    private String Prenom;
    @Column(name="Telephone")
    private String Telephone;
    @Column(name="Ville")
    private String ville;
    @Column(name="Permis")
    private String Npermis;
    @Column(name="Etat Global")
    private Boolean EtatGlobal;
    @Column(name="Etat Activité")
    private Boolean EtatActivité;
    @Column(name="Date de naissance")
    private LocalDate Naissance;
    @Column(name="Date d'inscription")
    private LocalDate dateInscription;
    @Column(name="Numéro dernier Payement")
    private int NdernierPayement;
    @Column(name="Email")
    private String email;
    @Column(name="Sexe")
    private String sexe;
    private static int Nbconducteur=0;
    public Conducteur() {}

    public Conducteur( String nom, String prenom, LocalDate naissance, String telephone, String ville, String email,String Npermis, String sexe) {
        Long l,g,k;
        char v = nom.charAt(0);
        l = (long) v;
        char p = prenom.charAt(0);
        g = (long) p;
        char t= telephone.charAt(4);
        k= (long) t;
        this.identifiant_C=l+g+k;
        this.Naissance = naissance;
        this.Nom = nom;
        this.Prenom = prenom;
        this.Telephone=telephone;
        this.ville = ville;
        this.email = email;
        this.Npermis=Npermis;
        this.sexe = sexe;
        this.dateInscription = LocalDate.now();
        this.NdernierPayement=0;
        Nbconducteur++;
    }


    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public Long getIdentifiant_C() {
        return identifiant_C;
    }

    public void setIdentifiant_C(Long identifiant_C) {
        this.identifiant_C = identifiant_C;
    }

    public String getNpermis() {
        return Npermis;
    }

    public void setNpermis(String npermis) {
        Npermis = npermis;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Boolean getEtatGlobal() {
        return EtatGlobal;
    }

    public void setEtatGlobal(Boolean etatGlobal) {
        EtatGlobal = etatGlobal;
    }

    public Boolean getEtatActivité() {
        return EtatActivité;
    }

    public void setEtatActivité(Boolean etatActivité) {
        EtatActivité = etatActivité;
    }

    public LocalDate getNaissance() {
        return Naissance;
    }

    public void setNaissance(LocalDate naissance) {
        Naissance = naissance;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public int getNdernierPayement() {
        return NdernierPayement;
    }

    public void setNdernierPayement(int ndernierPayement) {
        NdernierPayement = ndernierPayement;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public static int getNbconducteur() {
        return Nbconducteur;
    }

    public static void setNbconducteur(int nbconducteur) {
        Nbconducteur = nbconducteur;
    }
}
