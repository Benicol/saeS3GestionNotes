package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/** 
 * Controleur de la vue vue.VueExporter.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class VueExporterControleur {
    /** TexteField contenant l'adresse ip */
    @FXML
    private TextField adresseIpInput;
    
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
     * - Bouton "Etablir une connexion"
     */
    @FXML
    void boutonPleinEntree(MouseEvent event) {
        System.out.println("bouton plein entrer");
    }
    /**
     * Methode appeler lors de la sortie de la souris d'un bouton plein (bouton violet)
     * afin de le rééclaircir
     * Boutons utilisant cette méthode : 
     * - Bouton "Etablir une connexion"
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
        System.out.println("bouton vide entree");
    }
    /**
     * Methode appeler lors de l'entrée de la souris sur un bouton vide ou plein (on sait pas)
     * afin de le rendre plus foncer
     * Boutons utilisant cette méthode : 
     * - Bouton "Programme nationnal"
     * - Bouton "Modalités d'évaluation"
     */
    @FXML
    void boutonVideOuPleinEntree(MouseEvent event) {
        System.out.println("bouton vide ou plein entree");
    }
    /**
     * Methode appeler lors de la sortie de la souris sur un bouton vide ou plein (on sait pas)
     * afin de le rééclaircir
     * Boutons utilisant cette méthode : 
     * - Bouton "Programme nationnal"
     * - Bouton "Modalités d'évaluation"
     */
    @FXML
    void boutonVideOuPleinSortie(MouseEvent event) {
        System.out.println("bouton vide ou plein sortie");
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
     * Methode appeler lors du clic sur le bouton "Etablir une connexion"
     */
    @FXML
    void etablirUneConnexionPresser(ActionEvent event) {
        System.out.println("bouton etablir une connexion presser");
    }
    /**
     * Methode appeler lors du clic sur le bouton "Modalités d'évaluation"
     */
    @FXML
    void modalitesEvaluationPresser(ActionEvent event) {
        System.out.println("bouton modalite evaluation presser");
    }
    /**
     * Methode appeler lors du clic sur le bouton "Programme nationnal"
     */
    @FXML
    void programmeNationnalPresser(ActionEvent event) {
        System.out.println("bouton programme national presser");
    }

}