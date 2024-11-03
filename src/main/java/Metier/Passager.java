package Metier;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name = "Passager")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typePassager", discriminatorType = DiscriminatorType.STRING)
public class Passager implements Serializable {
    @Id
    private Long idendifiant_P;
    @Column(name="Nom")
    private String Nom;
    @Column(name="Prenom")
    private String prenom;
    @Column(name="Telephone")
    private String Telephone;
    @Column(name="ville")
    private String ville;
    @Column(name="Date d'inscription")
    private Date dateInscription;
    @Column(name="Email")
    private String Email;
    private static int Nbpassager=0;
    public Passager() {}

    public Passager(String nom, String prenom,String Telephone, String ville, String email) {
        Long l,g,k;
        char v = nom.charAt(0);
        l = (long) v;
        char p = prenom.charAt(0);
        g = (long) p;
        char t= Telephone.charAt(4);
        k= (long) t;
        this.idendifiant_P=l+g+k;
        this.Nom = nom;
        this.prenom = prenom;
        this.Telephone=Telephone;
        this.ville = ville;
        this.dateInscription = new Date();
        this.Email = email;
    }
    public Passager(String nom, String prenom,String Telephone, String ville) {
        this.Nom = nom;
        this.prenom = prenom;
        this.Telephone=Telephone;
        this.ville = ville;
        this.dateInscription = new Date();
    }


    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public Long getIdendifiant_P() {
        return idendifiant_P;
    }

    public void setIdendifiant_P(Long idendifiant_P) {
        this.idendifiant_P = idendifiant_P;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public static int getNbpassager() {
        return Nbpassager;
    }

    public static void setNbpassager(int nbpassager) {
        Nbpassager = nbpassager;
    }
}
