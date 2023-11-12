/*
 * VuePopUpReinitialiserController.java                                   9 nov 2023
 * IUT Rodez, pas de copyright
 */
package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import modele.Modele;

/** 
 * Contrôleur de la vue vue.VuePopUpReinitialiser.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class VuePopUpReinitialiserControleur {
    /**
     * Méthode appelée lors du clic sur le bouton "annuler"
     */
    @FXML
    void annulerPresser(ActionEvent event) {
        // Ferme la pop-up
        EchangeurDeVue.getPopUpStage().close();
    }
    /**
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'primary-button' (bouton plein violet) afin d'en changer le style
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
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'primary-button' (bouton plein violet) afin de remettre son style par 
     * défaut.
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
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets) afin
     * d'en changer le style
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
     * Méthode appelée lors de la sortie de la souris d'un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets) afin de 
     * remettre son style par défaut.
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
     * Méthode appelée lors du clic sur le bouton "Supprimer toutes les données"
     */
    @FXML
    void supprimerToutesLesDonneesPresser(ActionEvent event) {
        // Réinitialisation du modèle
        Modele.reset();
        EchangeurDeVue.getPopUpStage().close();
    }

}
