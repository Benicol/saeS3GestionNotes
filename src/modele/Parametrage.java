/*
 * Parametrage.java                                                       3 nov 2023                                                                                21/10/2015
 * IUT de Rodez, pas de copyright
 */
package modele;

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
public class Parametrage {
    
    /* Déclaration des variables qui composent le paramétrage */
    private String semestre;
    private String parcours;
    private HashMap<String, String> listeRessources;
    private String[][][] listeCompetences;
    private HashMap<String, String> listeSaes;

    /**
     * Constructeur de la classe Paramétrage, qui utilise les données passées en 
     * paramètres pour définir de quoi est composée le semestre.
     * @param semestre Le numéro du semestre en cours (un chiffre de 1 à 6).
     * @param parcours Le nom du parcours choisi (A, D ou "XXX" pour la 1e année).
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
        this.listeCompetences = donneesCompetences;
        this.listeSaes = new HashMap<>(donneesSaes);
        this.listeRessources = new HashMap<>(donneesRessources);
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
     * Retourne la liste des ressources associées au semestre.
     * @return La liste des ressources associées.
     */
    public HashMap<String, String> getListeRessources() {
        return listeRessources;
    }
    
    /**
     * Retourne la liste des compétences associées au semestre.
     * @return La liste des compétences associées.
     */
    public String[][][] getListeCompetences() {
        return listeCompetences;
    }
    
    /**
     * Retourne la liste des SAE (Situations d'Apprentissage et d'Évaluation) 
     * associées au semestre.
     * @return La liste des SAE associées.
     */
    public HashMap<String, String> getListeSaes() {
        return listeSaes;
    }
}