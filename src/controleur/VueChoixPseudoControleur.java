package controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Controleur de la vue vue.vueChoixPseudo.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class VueChoixPseudoControleur {
    /**
     * TexteField contenant le pseudo
     */
    @FXML
    private TextField pseudoInput;
    /**
     * Methode appeler lors du clic sur le bouton "annuler"
     */
    @FXML
    void annulerPresser(ActionEvent event) {
        System.out.println("annuler presser");
    }
    /**
     * Methode appeler lors de l'entrée de la souris sur un bouton plein (bouton violet)
     * afin de le rendre plus foncer
     * Boutons utilisant cette méthode : 
     * - Bouton "Changer de pseudo"
     */
    @FXML
    void boutonPleinEntree(MouseEvent event) {
        System.out.println("bouton plein entrer");
    }
    /**
     * Methode appeler lors de la sortie de la souris d'un bouton plein (bouton violet)
     * afin de le rééclaircir
     * Boutons utilisant cette méthode : 
     * - Bouton "Changer de pseudo"
     */
    @FXML
    void boutonPleinSortie(MouseEvent event) {
        System.out.println("bouton plein sortie");
    }
    /**
     * Methode appeler lors de l'entrée de la souris sur un bouton vide (bouton blanc)
     * afin de le rendre plus foncer
     * Boutons utilisant cette méthode : 
     * - Bouton "Annuler"
     */
    @FXML
    void boutonVideEntree(MouseEvent event) {
        System.out.println("bouton vide entrer");
    }
    /**
     * Methode appeler lors de la sortie de la souris d'un bouton vide (bouton blanc)
     * afin de le rééclaircir
     * Boutons utilisant cette méthode : 
     * - Bouton "Annuler"
     */
    @FXML
    void boutonVideSortie(MouseEvent event) {
        System.out.println("bouton vide sortie");
    }
    /**
     * Methode appeler lors du clic sur le bouton "Changer de Pseudo"
     */
    @FXML
    void changerDePseudoPresser(ActionEvent event) {
        System.out.println("changer de pseudo presser");
    }

}
