/**
 * Modele.java                                                                                 21/10/2015
 * No copyright.
 */
package modele;

/**
 * Classe Utilisateur.
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class Utilisateur {
    
    /**
     * Pseudo de l'utilisateur.
     */
    private String pseudo;

    /**
     * Pseudo par defaut.
     */
    private static final String PSEUDO_PAR_DEFAUT = "Utilisateur";

    /**
     * Constructeur de la classe Utilisateur avec un pseudo.
     * Si le pseudo est null ou vide, le pseudo par defaut est attribue.
     * @param pseudo le pseudo de l'utilisateur.
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
     * Setter du pseudo. Si le pseudo est null ou vide, le changement n'est pas effectue.
     * @param pseudo le pseudo de l'utilisateur.
     */
    public void setPseudo(String pseudo) {
        if (!(pseudo == null || pseudo.isEmpty())) {
            this.pseudo = pseudo;
        }
    }

}
