package Metier;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="Paiement")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typePaiement", discriminatorType = DiscriminatorType.STRING)
public class Paiement implements Serializable{
    @Id
    private Long id_paiement;
    @Column(name="Type")
    private String Type;
    @Column(name="Solde")
    private Double Solde;

    public Paiement(String type, Double solde) {
        this.Type = type;
        this.Solde = solde;
        if(type.toUpperCase().equals("MENSUEL")){
            this.id_paiement= 30L;
        } else if (type.toUpperCase().equals("TRIMESTUEL")) {
            this.id_paiement = 92L;
        }
        else this.id_paiement=366L;
    }

    public Paiement() {
    }

    public Long getId_paiement() {
        return id_paiement;
    }

    public void setId_paiement(Long id_paiement) {
        this.id_paiement = id_paiement;
    }

    public String getType() {
        return Type;
    }


    public void setType(String type) {
        Type = type;
    }

    public Double getSolde() {
        return Solde;
    }

    public void setSolde(Double solde) {
        Solde = solde;
    }
}
