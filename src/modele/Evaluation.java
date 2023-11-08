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
    private Double poids;
    private String date;
    private Double note;
    
    
    /**
     * Constructeur d'une note, qui instancie par défaut la note comme étant nulle.
     * @param nom de l'évaluation
     * @param poids le poids d'une évaluation
     * @param date la date à laquelle doit se dérouler l'évaluation (peut s'écrire
     * sous différente forme : 10/05/2023, ou encore "mi-mai", ...)
     */
    public Evaluation(String nom, Double poids, String date) {
        setNom(nom);
        setPoids(poids);
        setDate(date);
        this.note = null;
    }
    
    
    /**
     * Getter permettant d'obtenir le nom d'une évaluation
     * @return le nom de l'évaluation
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Getter permettant d'obtenir le poids d'une évaluation
     * @return le poids de l'évaluation
     */
    public Double getPoids() {
        return poids;
    }
    
    /**
     * Getter permettant d'obtenir la date d'une évaluation
     * @return la date de l'évaluation
     */
    public String getDate() {
        return date;
    }
    
    /**
     * Getter permettant d'obtenir la note associée à une évaluation
     * @return le note de l'évaluation
     */
    public Double getNote() {
        return note;
    }
    
    /**
     * Setter permettant de modifier le nom d'une évaluation
     * @param nom le nouveau nom choisi pour l'évaluation
     */
    public void setNom(String nom) {
        if (isNomValide(nom)) {
            this.nom = nom;
        } else {
            throw new IllegalArgumentException("Le nom choisi pour l'évaluation est"
                    + " incorrect");
        }
    }
    
    /**
     * Setter permettant de modifier le poids d'une évaluation
     * @param poids le nouveau poids choisi pour l'évaluation
     */
    public void setPoids(Double poids) {
        if (isPoidsValide(poids)) {
            this.poids = poids;        
        } else {
            throw new IllegalArgumentException("Le poids choisi pour l'évaluation "
                    + "est incorrect");
        }
    }
    
    /**
     * Setter permettant de modifier la date d'une évaluation
     * @param date la nouvelle date choisie pour l'évaluation
     */
    public void setDate(String date) {
        if (isDateValide(date)) {
            this.date = date; 
        } else {
            throw new IllegalArgumentException("La date choisie pour l'évaluation "
                    + "est incorrecte");
        }
    }
    
    /**
     * Setter permettant de modifier (ou de saisir) la note d'une évaluation
     * @param note la nouvelle note choisie pour l'évaluation
     */
    public void setNote(Double note) {
        if (isNoteValide(note)) {
            this.note = note;
        } else {
            throw new IllegalArgumentException("La note choisie pour l'évaluation "
                                              + "est incorrecte");
        }
    }
    
    /**
     * Méthode permettant de vérifier le nom d'une évaluation.
     * @param nom le nom à vérifier.
     * @return false si le nom n'est pas correct ("" ou null), true sinon.
     */
    private static boolean isNomValide(String nom) {
        return !nom.isBlank();
    }
    
    /**
     * Méthode permettant de vérifier le poids d'une évaluation.
     * @param poids le poids à vérifier.
     * @return false si le poids n'est pas correct (<0 ou >100), true sinon.
     */
    private static boolean isPoidsValide(Double poids) {
        return poids > 0 && poids <= 1;
    }
    
    /**
     * Méthode permettant de vérifier la date d'une évaluation.
     * @param date la date à vérifier.
     * @return false si la date n'est pas correct (""), true sinon.
     */
    private static boolean isDateValide(String date) {
        return !date.equals("");
    }
    
    /**
     * Méthode permettant de vérifier la date d'une évaluation.
     * @param note la note à vérifier.
     * @return false si la note n'est pas correct (<0 ou >20), true sinon.
     */
    private static boolean isNoteValide(Double note) {
        return note >= 0 && note <= 20;
    }

}
