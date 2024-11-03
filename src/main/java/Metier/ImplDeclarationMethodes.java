package Metier;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ImplDeclarationMethodes implements DeclarationMethodes, Serializable {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DonneesTaxi");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    private void closeResources() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Override
    public void addPassager(Passager passager) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            entityManager.persist(passager);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removePassager(int id) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            Passager p = entityManager.find(Passager.class, id);
            if (p != null) {
                entityManager.remove(p);
                et.commit();
            } else {
                et.rollback();
                System.out.println("Passager not found");
            }
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void updatePassager(Passager passager) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            entityManager.merge(passager);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Passager getPassagerById(int id) {
        Passager passager = null;
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            passager = entityManager.find(Passager.class, id);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return passager;
    }

    @Override
    public void addConducteur(Conducteur conducteur) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            entityManager.persist(conducteur);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeConducteur(int id) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            Conducteur c = entityManager.find(Conducteur.class, id);
            if (c != null) {
                entityManager.remove(c);
                et.commit();
            } else {
                et.rollback();
                System.out.println("Conducteur not found");
            }
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void updateConducteur(Conducteur conducteur) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            entityManager.merge(conducteur);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Conducteur getConducteurById(int id) {
        Conducteur conducteur = null;
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            conducteur = entityManager.find(Conducteur.class, id);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return conducteur;
    }

    @Override
    public void addPaiement(Paiement paiement) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            entityManager.persist(paiement);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removePaiement(int id) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            Paiement p = entityManager.find(Paiement.class, id);
            if (p != null) {
                entityManager.remove(p);
                et.commit();
            } else {
                et.rollback();
                System.out.println("Paiement not found");
            }
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void updatePaiement(Paiement paiement) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            entityManager.merge(paiement);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void addPayer(Payer payer) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            entityManager.persist(payer);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removePayer(Long id1, Long id2, LocalDate dateDebut, LocalDate dateFin) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            Payer p = entityManager.find(Payer.class, new CleConducteurPaiement(id1, id2, dateDebut, dateFin));
            if (p != null) {
                entityManager.remove(p);
                et.commit();
            } else {
                et.rollback();
                System.out.println("Payer not found");
            }
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void updatePayer(Payer payer) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            entityManager.merge(payer);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void addReserver(Reserver reserver) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            entityManager.persist(reserver);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeReserver(Long id1, Long id2, LocalDate date, LocalTime heure) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            Reserver r = entityManager.find(Reserver.class, new CleConducteurPassager(id1, id2, date, heure));
            if (r != null) {
                entityManager.remove(r);
                et.commit();
            } else {
                et.rollback();
                System.out.println("Reserver not found");
            }
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void updateReserver(Reserver reserver) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            entityManager.merge(reserver);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void addPayer(Long l1, Long l2,LocalDate d1, LocalDate d2) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        Payer payer;
        try {
            Conducteur conducteur=entityManager.find(Conducteur.class,l1);
            Paiement paiement=entityManager.find(Paiement.class,l2);
            payer = new Payer(conducteur,paiement);
            payer.setDateDebut(d1);
            payer.setDateFin(d2);
            entityManager.merge(payer);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Passager> getPassagers() {
        List<Passager> passagers = new ArrayList<>();
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            passagers = entityManager.createQuery("SELECT c FROM Passager c", Passager.class).getResultList();
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return passagers;
    }

    @Override
    public List<Conducteur> getConducteurs() {
        List<Conducteur> conducteurs = new ArrayList<>();
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try {
            conducteurs = entityManager.createQuery("SELECT c FROM Conducteur c", Conducteur.class).getResultList();
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return conducteurs;
    }
}
