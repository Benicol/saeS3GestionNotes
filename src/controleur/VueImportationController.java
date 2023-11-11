package controleur;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import modele.Modele;

/** 
 * Controleur de la vue vue.Importation.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, jodie.monterde
 */
public class VueImportationController {
    
    @FXML
    void initialize() {
        EchangeurDeVue.getPrimaryStage().setMaximized(false);
        EchangeurDeVue.getPrimaryStage().setHeight(500);
        EchangeurDeVue.getPrimaryStage().setWidth(900);
    }

    @FXML
    private Button boutonUtilisateur;

    @FXML
    void utilisateurPresser(ActionEvent event) {

    }

    /**
     * Methode appelée lors que la souris rentre sur bouton Utilisateur
     */
    @FXML
    void utilisateurEntree(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("utilisateur-not-hover");
        bouton.getStyleClass().add("utilisateur-hover");
    }

    /**
     * Methode appelée lors que la souris sort du bouton Utilisateur
     */
    @FXML
    void utilisateurSortie(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("utilisateur-hover");
        bouton.getStyleClass().add("utilisateur-not-hover");
    }
    
    @FXML
    void boutonImporterPresser(ActionEvent event) {
        EchangeurDeVue.launchPopUp(null, null);
    }
    
    @FXML
    void onDragDropped(DragEvent event) {
        System.out.println("hi");
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    @FXML
    void onDragOver(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) {
            // Récupérer le fichier déposé
            String file = dragboard.getFiles().get(0).getPath();
            System.out.println("Fichier déposé : " + file);
            Modele.importer(file);
            success = true;
            
        }
        event.setDropCompleted(success);
        event.consume();
        if (success) {
            EchangeurDeVue.echangerAvec("h", false);
        }
    }
}
