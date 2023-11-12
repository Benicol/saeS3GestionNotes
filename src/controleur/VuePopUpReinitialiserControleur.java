package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import modele.Modele;

/** 
 * Controleur de la vue vue.VueReinitialiser.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class VuePopUpReinitialiserControleur {
    /**
     * Methode appelée lors du clic sur le bouton "annuler"
     */
    @FXML
    void annulerPresser(ActionEvent event) {
        // Ferme le popup
        EchangeurDeVue.getPopUpStage().close();
    }
    /**
     * Methode appelée lors de l'entrée de la souris sur un bouton plein (bouton violet)
     * afin de le rendre plus foncé
     * Boutons utilisant cette méthode : 
     * - Bouton "Supprimer toutes les données"
     */
    @FXML
    void primaryButtonEntered(MouseEvent event) {
        // Récupère le bouton appelant
        Button bouton = (Button) event.getSource();
        
        // Change la couleur du bouton
        bouton.getStyleClass().remove("primary-button-not-hover");
        bouton.getStyleClass().add("primary-button-hover");
    }
    /**
     * Methode appelée lors de la sortie de la souris d'un bouton plein (bouton violet)
     * afin de le rééclaircir
     * Boutons utilisant cette méthode : 
     * - Bouton "Supprimer toutes les données"
     */
    @FXML
    void primaryButtonExited(MouseEvent event) {
        // Récupère le bouton appelant
        Button bouton = (Button) event.getSource();
        
        // Change la couleur du bouton
        bouton.getStyleClass().remove("primary-button-hover");
        bouton.getStyleClass().add("primary-button-not-hover");
    }
    /**
     * Methode appelée lors de l'entrée de la souris sur un bouton vide (bouton blanc)
     * afin de le rendre plus foncé
     * Boutons utilisant cette méthode : 
     * - Bouton "Annuler"
     */
    @FXML
    void secondaryButtonEntered(MouseEvent event) {
        // Récupère le bouton appelant
        Button bouton = (Button) event.getSource();
        
        // Change la couleur du bouton
        bouton.getStyleClass().remove("secondary-button-not-hover");
        bouton.getStyleClass().add("secondary-button-hover");
    }
    /**
     * Methode appelée lors de la sortie de la souris d'un bouton vide (bouton blanc)
     * afin de le rééclaircir
     * Boutons utilisant cette méthode : 
     * - Bouton "Annuler"
     */
    @FXML
    void secondaryButtonExited(MouseEvent event) {
        // Récupère le bouton appelant
        Button bouton = (Button) event.getSource();
        
        // Change la couleur du bouton
        bouton.getStyleClass().remove("secondary-button-hover");
        bouton.getStyleClass().add("secondary-button-not-hover");
    }
    /**
     * Methode appeler lors du clic sur le bouton "Supprimer toutes les données"
     */
    @FXML
    void supprimerToutesLesDonneesPresser(ActionEvent event) {
        // Réinitialisation du modèle
        Modele.reset();
        EchangeurDeVue.getPopUpStage().close();
    }

}
