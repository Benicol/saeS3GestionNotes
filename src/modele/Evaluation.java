/*
 * Evaluation.java                                                       31 Oct 2023
 * IUT Rodez, pas de copyright ni "copyleft" 
 */
package modele;

/**
 * Classe Evaluation, permet de stocker les informations propres à une évaluation :
 *     - le nom d'une évaluation
 *     - le poids (pondération) d'une évaluation
 *     - la date à laquelle a lieu l'évaluation (non obligatoire)
 *     - la note obtenue pour cette évaluation (non obligatoire)
 *     
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class Evaluation {
    
    /* Déclaration des variables */
    private String nom;
    private int poids;
    private String date;
    private Double note;
    
    
    /**
     * Constructeur d'une note, qui instancie par défaut la note comme étant nulle.
     * @param nom de l'évaluation
     * @param poids le poids d'une évaluation
     * @param date la date à laquelle doit se dérouler l'évaluation (peut s'écrire
     * sous différente forme : 10/05/2023, ou encore "mi-mai", ...)
     */
    public Evaluation(String nom, int poids, String date) {
        this.nom = nom;
        this.poids = poids;
        this.date = date;
        this.note = null;
    }
    
    
    /**
     * Getteur permettant d'obtenir le nom d'une évaluation
     * @return le nom de l'évaluation
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Getteur permettant d'obtenir le poids d'une évaluation
     * @return le poids de l'évaluation
     */
    public int getPoids() {
        return poids;
    }
    
    /**
     * Getteur permettant d'obtenir la date d'une évaluation
     * @return la date de l'évaluation
     */
    public String getDate() {
        return date;
    }
    
    /**
     * Getteur permettant d'obtenir la note associée à une évaluation
     * @return le note de l'évaluation
     */
    public double getNote() {
        return note;
    }
    
    /**
     * Setteur permettant de modifier le nom d'une évaluation
     * @param nom le nouveau nom choisi pour l'évaluation
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * Setteur permettant de modifier le poids d'une évaluation
     * @param poids le nouveau poids choisi pour l'évaluation
     */
    public void setPoids(int poids) {
        this.poids = poids;
    }
    
    /**
     * Setteur permettant de modifier la date d'une évaluation
     * @param date la nouvelle date choisie pour l'évaluation
     */
    public void setDate(String date) {
        this.date = date;
    }
    
    /**
     * Setteur permettant de modifier (ou de saisir) la note d'une évaluation
     * @param note la nouvelle note choisie pour l'évaluation
     */
    public void setNote(double note) {
        this.note = note;
    }

}
