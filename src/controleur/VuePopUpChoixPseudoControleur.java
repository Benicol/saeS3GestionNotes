/*
 * VuePopUpChoixPseudoControleur.java                                          9 nov 2023
 * IUT Rodez, pas de copyright
 */
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
     * Méthode appelée lors du clic sur le bouton "annuler"
     */
    @FXML
    void annulerPresser(ActionEvent event) {
        EchangeurDeVue.getPopUpStage().close();
    }
    /**
     * Méthode appelée lors de l'entrée de la souris sur un bouton plein 
     * (bouton violet) afin de le rendre plus foncé
     * Boutons utilisant cette méthode : 
     * - Bouton "Etablir une connexion"
     */
    @FXML
    void primaryButtonEntered(MouseEvent event) {
        // Va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // Change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("primary-button-not-hover");
        bouton.getStyleClass().add("primary-button-hover");
    }
    /**
     * Méthode appelée lors de la sortie de la souris d'un bouton plein 
     * (bouton violet) afin de le rééclaircir
     * Boutons utilisant cette méthode : 
     * - Bouton "Etablir une connexion"
     */
    @FXML
    void primaryButtonExited(MouseEvent event) {
        // Va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // Change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("primary-button-hover");
        bouton.getStyleClass().add("primary-button-not-hover");
    }
    /**
     * Méthode appelée lors de l'entrée de la souris sur un bouton vide 
     * (bouton blanc) afin de le rendre plus foncé
     * Boutons utilisant cette méthode : 
     * - Bouton "Annuler"
     */
    @FXML
    void secondaryButtonEntered(MouseEvent event) {
        // Va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // Change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("secondary-button-not-hover");
        bouton.getStyleClass().add("secondary-button-hover");
    }
    
    /**
     * Methode appelée lors de la sortie de la souris d'un bouton vide 
     * (bouton blanc) afin de le rééclaircir
     * Boutons utilisant cette méthode : 
     * - Bouton "Annuler"
     */
    @FXML
    void secondaryButtonExited(MouseEvent event) {
        // Va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // Change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("secondary-button-hover");
        bouton.getStyleClass().add("secondary-button-not-hover");
    }
    /**
     * Méthode appelée lors du clic sur le bouton "Changer de Pseudo"
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
    	        messageErreur = "il ne doit pas dépasser les 40 caractères !";
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
