/*
 * VuePopUpImporterController.java                                        9 nov 2023
 * IUT Rodez, pas de copyright
 */
package controleur;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import modele.Modele;
import modele.OutilReseau;

/** 
 * Contrôleur de la vue vue.VueExporter.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class VuePopUpImporterControleur {
    
    /**
     * Méthode appelée lors du clic sur le bouton "afficher l'adresse IP"
     * Cette méthode permet simplement de changer le texte du bouton
     * en l'adresse IP de l'utilisateur
     */
    @FXML
    void afficherAdresseIPPresser(ActionEvent event) {        
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        if (bouton.getText().equals("AFFICHER L'ADRESSE IP")) {
            bouton.setText("ADRESSE: " + OutilReseau.getIp());
        } else {
            bouton.setText("AFFICHER L'ADRESSE IP");
        }
        
    }
    
    /**
     * Méthode appelée lors du clic sur le bouton "annuler"
     */
    @FXML
    void annulerPresser(ActionEvent event) {
        EchangeurDeVue.getPopUpStage().close();
    }
    
    /**
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'primary-button' (bouton plein violet) afin d'en changer le style
     * Boutons utilisant cette méthode : 
     * - Bouton "Importer"
     * - Bouton "Afficher l'asresse IP"
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
     * Méthode appelée lors de la sortie de la souris d'un bouton de 
     * style 'primary-button' (bouton plein violet) afin de remettre son style par 
     * défaut.
     * Boutons utilisant cette méthode : 
     * - Bouton "Importer"
     * - Bouton "Afficher l'adresse IP"
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
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets) afin
     * d'en changer le style
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
     * Méthode appelée lors de la sortie de la souris d'un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets) afin de 
     * remettre son style par défaut.
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
     * Méthode appelée lors du clic sur le bouton "Etablir une connexion"
     * Méthode NON IMPLEMENTEE 
     * (change le texte du bouton en "Implémentation en cours"
     */
    @FXML
    void etablirUneConnexionPresser(ActionEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        bouton.setText("Implémentation en cours");
        
        // TODO : coder la méthode
    }
    
    
    /**
     * Méthode appelée lors du clic sur le bouton "Importer"
     * Lance un explorateur de fichier afin que l'utilisateur puisse 
     * sélectionner un .csv et lance donc la méthode du Modele d'importer.
     * Si le fichier choisi est invalide,  alors affiche une Pop-Up expliquant les 
     * raison de l'échec de l'importation, si l'importation s'est bien déroulée,
     * la Pop-Up confirme le bon déroulement de l'importation et l'utilisateur 
     * retourne sur la homepage.
     */
    @FXML
    void importerPresser(ActionEvent event) {
        Button openExplorerButton = (Button) event.getSource();

        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) openExplorerButton.getScene().getWindow();
        
        // Configure le FileChooser pour n'afficher que les fichiers .csv
        fileChooser.setTitle("Sélectionnez un fichier CSV");
        ExtensionFilter filter = new ExtensionFilter("Fichiers CSV", "*.csv");
        fileChooser.getExtensionFilters().add(filter);

        // Affiche la fenêtre de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                // Appel de la méthode qui peut potentiellement lancer une exception
                Modele.importer(selectedFile.toString());
                EchangeurDeVue.getPopUpStage().close();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Test");
                alert.setHeaderText("Paramétrage effectué");
                alert.setContentText("L'importation s'est déroulée sans accroc !");
                alert.showAndWait();
                
            } catch (IllegalArgumentException e) {
                // Gestion de l'exception
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("L'importation a rencontrée un problème...");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
