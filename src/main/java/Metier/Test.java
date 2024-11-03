package Metier;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        ImplDeclarationMethodes id= new ImplDeclarationMethodes();
        Passager passager = new Passager("arsalane","aymane","56782334995","Mohammedia","koteseydou8@gmail.com");
        Passager passager1 = new Passager("Kote","arsalane","56782334995","Mohammedia","koteseydou8@gmail.com");
        Conducteur conducteur = new Conducteur("Kote","arsalane", LocalDate.now(),"23456888","Mohammedia","egshjjfjfj@gmail.com","23456ERTYUI","homme");
        Conducteur conducteur1 = new Conducteur("Dakio","Isaa", LocalDate.now(),"23456888","Mohammedia","egshjjfjfj@gmail.com","23456ERTYUI","homme");
        id.addPassager(passager);
        id.addPassager(passager1);
        id.addConducteur(conducteur);
        id.addConducteur(conducteur1);
        Paiement paiement= new Paiement("mensuel", 50.0);
        id.addPaiement(paiement);
        Payer payer = new Payer(conducteur,paiement);
        id.addPayer(payer);
        Reserver reserver = new Reserver(conducteur,passager1);
        id.addReserver(reserver);
        Paiement paiement1= new Paiement("trimestuel", 140.0);
        id.addPaiement(paiement1);
        Paiement paiement2= new Paiement("annuelle", 500.0);
        id.addPaiement(paiement2);
    }
}
