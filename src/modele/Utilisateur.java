package modele;

/**
 * Modele.java                                                                                 21/10/2015
 * No copyright.
 */
public class Utilisateur {
    /**
     * //TODO
     */
    private String pseudo;

    /**
     * //TODO
     */
    private static final String PSEUDO_PAR_DEFAUT = "Utilisateur";

    /**
     * //TODO
     */
    public Utilisateur(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * //TODO
     */
    public Utilisateur() {
        this(PSEUDO_PAR_DEFAUT);
    }

    /**
     * //TODO
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * //TODO
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

}
