/*
 * Parametrage.java                                                       3 nov 2023                                                                                21/10/2015
 * IUT de Rodez, pas de copyright
 */
package modele;

import java.io.Serializable;
import java.util.HashMap;

/**
 * La classe Parametrage représente un paramétrage associé à un semestre dans un 
 * parcours. Elle stocke des informations telles que :
 *     - le numéro du semestre
 *     - le nom du parcours,
 *     - les compétences associées
 *     - les SAE (Situations d'Apprentissage et d'Évaluation) associées
 *     - les ressources associées à ce semestre.
 * Les méthodes de cette classe permettent d'accéder aux informations du paramétrage.
 * 
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class Parametrage implements Serializable{
    
    private static final long serialVersionUID = 1L;
    /* Déclaration des variables qui composent le paramétrage */
    private String semestre;
    private String parcours;
    private HashMap<String,Ressource> listeRessources;
    private HashMap<String,Competence> listeCompetences;
    private HashMap<String,Sae> listeSaes;

    /**
     * Constructeur de la classe Paramétrage, qui utilise les données passées en 
     * paramètres pour définir de quoi est composée le semestre.
     * @param semestre Le numéro du semestre en cours (un chiffre de 1 à 6).
     * @param parcours Le nom du parcours choisi (A, D ou "Tous" pour la 1e année).
     * @param donneesCompetences La liste des compétences associées à ce semestre.
     * @param donneesSaes La liste des SAE associées à ce semestre.
     * @param donneesRessources La liste des ressources associées à ce semestre.
     */
    public Parametrage(String semestre, 
                       String parcours, 
                       String[][][] donneesCompetences, 
                       HashMap<String, String> donneesSaes, 
                       HashMap<String, String> donneesRessources) {
        if (semestre == null || parcours == null || donneesCompetences == null 
           || donneesSaes == null || donneesRessources == null) {
            throw new IllegalArgumentException("Aucun paramètre ne peut être nul.");
        }

        this.semestre = semestre;
        this.parcours = parcours;
        this.listeRessources = new HashMap<>();
        for (String identifiant : donneesRessources.keySet()) {
            listeRessources.put(identifiant, new Ressource(
                    identifiant, donneesRessources.get(identifiant)));
        }
        this.listeSaes = new HashMap<>();
        for (String identifiant : donneesSaes.keySet()) {
            listeSaes.put(identifiant, new Sae(identifiant, donneesSaes.get(
                    identifiant)));
        }
        this.listeCompetences = new HashMap<>();
        for (String[][] competence : donneesCompetences) {
            String identifiant = competence[0][0];
            listeCompetences.put(identifiant, new Competence(
                    identifiant, competence[0][1]));
            for (int i = 1; i < competence.length; i++) {
                if (competence[i][0].charAt(0) == 'R') {
                    listeCompetences.get(identifiant).getListeRessources().put(
                            listeRessources.get(competence[i][0]), 
                            Double.parseDouble(competence[i][1])/100);
                } else if (competence[i][0].charAt(0) == 'S' 
                        || competence[i][0].charAt(0) == 'P') {
                    listeCompetences.get(identifiant).getListeSaes().put(
                            listeSaes.get(competence[i][0]), Double.parseDouble(
                                    competence[i][1])/100);
                } else {
                    throw new IllegalArgumentException("Le type est inconnu.");
                }
            }
        }
    }

    
    /**
     * Retourne le numéro du semestre.
     * @return Le numéro du semestre.
     */
    public String getSemestre() {
        return semestre;
    }
    
    /**
     * Retourne le nom du parcours.
     * @return Le nom du parcours.
     */
    public String getParcours() {
        return parcours;
    }
    
    /**
     * Retourne la table de hachage des ressources associées au semestre.
     * @return La liste des ressources associées.
     */
    public HashMap<String, Ressource> getListeRessources() {
        return listeRessources;
    }
    
    /**
     * Retourne la ta le de hachage des compétences associées au semestre.
     * @return La liste des compétences associées.
     */
    public HashMap<String, Competence> getListeCompetences() {
        return listeCompetences;
    }
    
    /**
     * Retourne la table de hachage des SAE (Situations d'Apprentissage et 
     * d'Évaluation) associées au semestre.
     * @return La liste des SAE associées.
     */
    public HashMap<String, Sae> getListeSaes() {
        return listeSaes;
    }
}