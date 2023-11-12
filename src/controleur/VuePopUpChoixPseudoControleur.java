package controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import modele.Modele;

/**
 * Controleur de la vue vue.vueChoixPseudo.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class VuePopUpChoixPseudoControleur {
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
        EchangeurDeVue.getPopUpStage().close();
    }
    /**
     * Methode appeler lors de l'entrée de la souris sur un bouton plein (bouton violet)
     * afin de le rendre plus foncer
     * Boutons utilisant cette méthode : 
     * - Bouton "Etablir une connexion"
     */
    @FXML
    void primaryButtonEntered(MouseEvent event) {
     // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // On change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("primary-button-not-hover");
        bouton.getStyleClass().add("primary-button-hover");
    }
    /**
     * Methode appeler lors de la sortie de la souris d'un bouton plein (bouton violet)
     * afin de le rééclaircir
     * Boutons utilisant cette méthode : 
     * - Bouton "Etablir une connexion"
     */
    @FXML
    void primaryButtonExited(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("primary-button-hover");
        bouton.getStyleClass().add("primary-button-not-hover");
    }
    /**
     * Methode appeler lors de l'entrée de la souris sur un bouton vide (bouton blanc)
     * afin de le rendre plus foncer
     * Boutons utilisant cette méthode : 
     * - Bouton "Annuler"
     */
    @FXML
    void secondaryButtonEntered(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // On change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("secondary-button-not-hover");
        bouton.getStyleClass().add("secondary-button-hover");
    }
    
    /**
     * Methode appeler lors de la sortie de la souris d'un bouton vide (bouton blanc)
     * afin de le rééclaircir
     * Boutons utilisant cette méthode : 
     * - Bouton "Annuler"
     */
    @FXML
    void secondaryButtonExited(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("secondary-button-hover");
        bouton.getStyleClass().add("secondary-button-not-hover");
    }
    /**
     * Methode appeler lors du clic sur le bouton "Changer de Pseudo"
     */
    @FXML
    void changerDePseudoPresser(ActionEvent event) {
        String messageErreur = null;
    	if (!pseudoInput.getText().trim().equals("")) {
    	    if (pseudoInput.getText().trim().length() < 40) {
    	        Modele.getUtilisateur().setPseudo(pseudoInput.getText());
    	        Modele.sauvegarder();
    	        EchangeurDeVue.getPopUpStage().close();
    	    } else {
    	        messageErreur = "il fait plus de 40 lettres !";
    	    }
    	} else {
    	    messageErreur = "vous n'avez pas saisi de pseudo !";
    	}
    	if (messageErreur != null) {
    	    Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("PSEUDO INVALIDES");
            alert.setHeaderText("Votre pseudo est invalide : " + messageErreur);
            alert.showAndWait();
    	}
    }

}
