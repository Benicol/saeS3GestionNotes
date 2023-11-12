package controleur;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import modele.Modele;
import modele.OutilReseau;

/** 
 * Controleur de la vue vue.VueExporter.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class VuePopUpImporterControleur {
    
    /**
     * Methode appeler lors du clic sur le bouton "afficher l'adresse IP"
     * Cette méthode permet simplement dans changer le texte du bouton appelant
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
     * Methode appeler lors de la sortie de la souris d'un bouton plein (bouton violet)
     * afin de le rééclaircir
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
     * Methode appeler lors du clic sur le bouton "Etablir une connexion"
     * Methode ne faisant rien pour l'instant (change le texte du bouton en "NON IMPLEMENTER"
     */
    @FXML
    void etablirUneConnexionPresser(ActionEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        bouton.setText("Implémentation en cours");
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
