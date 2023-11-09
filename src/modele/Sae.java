/*
 * Sae.java                                  2 nov. 2023
 * IUT Rodez, info1 2022-2023, ni copyright ni "copyleft"
 */
package modele;

import java.io.Serializable;

/** Classe objet permettant de représenter une Sae
 * à partir d'un identifiant et d'un libelle,
 * et permettant d'y associer une note.
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class Sae implements Enseignement, Serializable{
    private static final long serialVersionUID = 1L;
    private String libelle;
    private String identifiant;
    private Double note;

    /** Constructeur de la classe Sae
     * @param identifiant
     * @param libelle
     */
    public Sae(String identifiant, String libelle) {
        if (libelle == null || libelle.equals("") || identifiant == null || identifiant.equals("")) {
            throw new IllegalArgumentException();
        }
        this.libelle = libelle;
        this.identifiant = identifiant;
        this.note = null;
    }
    
    /** Getter de l'attribut note
     * @return note
     */
    public Double getNote() {
        return note;
    }
    
    /** Setter de l'attribut note
     * @param note
     */
    public void setNote(Double note) {
        if (note < 0 || note > 20) {
            throw new IllegalArgumentException();
        }
        this.note = note;
    }
    
    /** Getter de l'attribut libelle
     * @return libelle
     */
    public String getLibelle() {
        return libelle;
    }
    
    /** Getter de l'attribut identifiant
     * @return identifiant
     */
    public String getIdentifiant() {
        return identifiant;
    }
    
    /** Renvoie la combinaison de l'identifiant et du libelle
     * @return intitule
     */
    public String creerIntitule() {
        String intitule = this.identifiant + " : " + this.libelle;
        return intitule;
    }
}
