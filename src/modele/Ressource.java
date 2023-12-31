/*
 * Ressource.java                                                         7 nov 2023
 * IUT de Rodez, pas de droit d'auteur
 */

package modele;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Cette classe représente une ressource liée à un enseignement. Elle permet de
 * gérer des évaluations associées à cette ressource afin de pouvoir :
 *      - en ajouter
 *      - en supprimer
 *      - en modifier
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class Ressource implements Enseignement, Serializable {
    
    private static final long serialVersionUID = 1L;

    /* Liste des évaluations associées à la ressource */
    private ArrayList<Evaluation> listeEvaluations;
    
    /* Libellé de la ressource */
    private String libelle;
    
    /* Identifiant de la ressource */
    private String identifiant;

    /**
     * Constructeur de la classe Ressource.
     * @param identifiant L'identifiant de la ressource.
     * @param libelle Le libellé de la ressource.
     */
    public Ressource(String identifiant, String libelle) {
        this.libelle = libelle;
        this.identifiant = identifiant;
        listeEvaluations = new ArrayList<>();
    }

    /**
     * Ajoute une évaluation à la liste des évaluations de la ressource.
     * @param aAjouter L'évaluation à ajouter.
     * @return true si l'ajout a réussi, sinon false.
     */
    public boolean ajouterEvaluation(Evaluation aAjouter) {
        boolean ajoutOk;
        ajoutOk = true;
        listeEvaluations.add(aAjouter);
        if (!verifierPonderation()) {
            listeEvaluations.remove(aAjouter);
            ajoutOk = false;
        }
        return ajoutOk;
    }

    /**
     * Supprime une évaluation de la liste des évaluations de la ressource.
     * @param idEvaluation L'identifiant de l'évaluation à supprimer.
     * @return true si la suppression a réussi, sinon false.
     */
    public boolean supprimerEvaluation(int idEvaluation) {
        boolean suprOk;
        suprOk = false;
        if (idEvaluation <= listeEvaluations.size() && idEvaluation >= 0) {
            suprOk = true;
            listeEvaluations.remove(idEvaluation);
        }
        return suprOk;
    }

    /**
     * Vérifie si les pondérations des évaluations sont valides (somme entre 0 et 100).
     * @param ponderations La liste des pondérations des évaluations.
     * @return true si les pondérations sont valides, sinon false.
     */
    private boolean verifierPonderation() {
        double poidsTotal = getPoidsTotal();
        return poidsTotal >= 0 && poidsTotal <= 1;
    }
    /**
     * Vérifie si les pondérations des évaluations sont valides (somme entre 0 et 100).
     * @param ponderations La liste des pondérations des évaluations.
     * @return true si les pondérations sont valides, sinon false.
     */
    private boolean verifierPonderationComplete() {
        return getPoidsTotal() == 1;
    }
    
    

    /**
     * Obtient le libellé de la ressource.
     * @return Le libellé de la ressource.
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Obtient l'identifiant de la ressource.
     * @return L'identifiant de la ressource.
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * Crée un intitulé de la ressource en combinant le libellé et l'identifiant.
     * @return L'intitulé de la ressource.
     */
    public String creerIntitule() {
        return this.getIdentifiant() + " : " + this.getLibelle();
    }

    /**
     * Obtient la liste des évaluations.
     * @return La liste des évaluations.
     */
    public ArrayList<Evaluation> getListeEvaluations() {
        return listeEvaluations;
    }

    /**
     * Calcule la moyenne des évaluations si elles sont calculables et que la 
     * somme des pondérations est égale à 100.
     * @return La moyenne des évaluations, sinon 0.0.
     */
    public Double calculerMoyenne() {
        double moyenne;
        moyenne = 0.0;
        if (isCalculable()) {
            for (int i = 0; i < listeEvaluations.size(); i++) {
                moyenne += listeEvaluations.get(i).getNote() 
                        * listeEvaluations.get(i).getPoids();
            }
        }
        return moyenne;
    }

    /**
     * Vérifie si les évaluations sont calculables en tenant compte des notes et
     * des pondérations.
     * @return true si les évaluations sont calculables, sinon false.
     */
    public boolean isCalculable() {
        boolean calculOk;
        calculOk = false;
        if (verifierPonderationComplete()) {
            calculOk = true;
            for (int i = 0; i < this.listeEvaluations.size() && calculOk; i++) {
                if (listeEvaluations.get(i).getNote() == null) { 
                    calculOk = false;
                }
            }
        }
        return calculOk;
    }

    /**
     * Obtient la liste des pondérations des évaluations.
     * @return La liste des pondérations.
     */
    private ArrayList<Double> getPonderations() {
        ArrayList<Double> ponderations;
        ponderations = new ArrayList<>();

        for (int i = 0; i < listeEvaluations.size(); i++) {
            ponderations.add(listeEvaluations.get(i).getPoids());
        }
        return ponderations;

    }

    /**
     * Permet d'obtenir le poids total de toutes les évaluations
     * @return le poids total des évaluations.
     */
    private double getPoidsTotal() {
        double poidsTotal = 0;
        for (int i = 0; i < this.getPonderations().size(); i++) {
            poidsTotal += this.getPonderations().get(i);
        }
        return poidsTotal;
    }
}
