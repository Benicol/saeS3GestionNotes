package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/** 
 * Controleur de la vue vue.VueExporter.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class VueImporterControleur {
    /**
     * Methode appeler lors du clic sur le bouton "afficher l'adresse IP"
     * Cette méthode permet simplement dans changer le texte du bouton appelant
     * en l'adresse IP de l'utilisateur
     */
    @FXML
    void afficherAdresseIPPresser(ActionEvent event) {
        System.out.println("bouton afficher ip appuyer");
    }
    /**
     * Methode appeler lors du clic sur le bouton "annuler"
     */
    @FXML
    void annulerPresser(ActionEvent event) {
        System.out.println("bouton annuler presser");
    }
    /**
     * Methode appeler lors de l'entrée de la souris sur un bouton plein (bouton violet)
     * afin de le rendre plus foncer
     * Boutons utilisant cette méthode : 
     * - Bouton "Importer"
     * - Bouton "Afficher l'asresse IP"
     * - Bouton "Etablir une connexion"
     */
    @FXML
    void boutonPleinEntree(MouseEvent event) {
        System.out.println("bouton plein entre");
    }
    /**
     * Methode appeler lors de la sortie de la souris d'un bouton plein (bouton violet)
     * afin de le rééclaircir
     * Boutons utilisant cette méthode : 
     * - Bouton "Importer"
     * - Bouton "Afficher l'asresse IP"
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
     * Methode ne faisant rien pour l'instant (change le texte du bouton en "NON IMPLEMENTER"
     */
    @FXML
    void etablirUneConnexionPresser(ActionEvent event) {
        System.out.println("bouton etablir une connexion presser");
    }
    /**
     * Methode appeler lors du clic sur le bouton "Importer"
     * Lance un explorateur de fichier afin que l'utilisateur puisse 
     * séléctionner un .csv et lance donc la méthode du Modele d'importer,
     * si invalide alors affiche un label rouge à gauche d'importer disant 
     * que les données sont invalides, si l'importation c'est bien passer 
     * alors ce label est en vert et dit que tout c'est bien passer
     */
    @FXML
    void importerPresser(ActionEvent event) {
        System.out.println("bouton importer appuyer");
    }

}
