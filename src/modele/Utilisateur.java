/*
 * Modele.java                                                        21/10/2023
 * IUT de Rodez, pas de droit d'auteur.
 */
package modele;

import java.io.Serializable;

/**
 * Classe permettant de stocker et de manipuler les données liées à un 
 * utilisateur de l'application :
 *     - son pseudo
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class Utilisateur implements Serializable{
    
    private static final long serialVersionUID = 1L;

    /* Pseudo de l'utilisateur */
    private String pseudo;

    /* Pseudo par defaut */
    private static final String PSEUDO_PAR_DEFAUT = "Utilisateur";

    /**
     * Constructeur de la classe Utilisateur.
     * Si le pseudo est null ou vide, le pseudonyme par defaut est attribué.
     * @param pseudo le pseudonyme utilisé pour désigner de l'utilisateur.
     */
    public Utilisateur(String pseudo) {
        if (pseudo == null || pseudo == "") {
            this.pseudo = PSEUDO_PAR_DEFAUT;
        } else {
            this.pseudo = pseudo;
        }
    }

    /** 
     * Constructeur de la classe Utilisateur sans pseudo.
     * Le pseudo qui sera utilisé pour désigner l'utilisateur est donc celui par
     * défaut.
     */
    public Utilisateur() {
        this.pseudo = PSEUDO_PAR_DEFAUT;
    }

    /**
     * Getter du pseudo.
     * @return le pseudo de l'utilisateur.
     */
    public String getPseudo() {
        return this.pseudo;
    }

    /**
     * Setter du pseudo. 
     * Si le pseudo est null ou vide, le changement ne s'effectue pas.
     * @param pseudo le pseudo de l'utilisateur.
     */
    public void setPseudo(String pseudo) {
        if (!(pseudo == null || pseudo.isEmpty())) {
            this.pseudo = pseudo;
        }
    }
}
