/*
 * VueImportationController.java                                             9 nov 2023
 * IUT Rodez, pas de copyright
 */
package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import modele.Modele;
import modele.Ressource;

/** 
 * Contrôleur de la vue vue.VueImportation.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, jodie.monterde
 */
public class VueImportationController {
    
    @FXML
    void initialize() {
        EchangeurDeVue.getPrimaryStage().setMaximized(false);
        EchangeurDeVue.getPrimaryStage().setHeight(600);
        EchangeurDeVue.getPrimaryStage().setWidth(1000);
        boutonUtilisateur.setText(Modele.getUtilisateur().getPseudo());
    }

    @FXML
    private Button boutonUtilisateur;

    @FXML
    void utilisateurPresser(ActionEvent event) {
        EchangeurDeVue.launchPopUp("vcp", "Changement de nom d'utilisateur");
        boutonUtilisateur.setText(Modele.getUtilisateur().getPseudo());
    }

    /**
     * Méthode appelée lorsque la souris entre sur le bouton Utilisateur
     */
    @FXML
    void userEntered(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("utilisateur-not-hover");
        bouton.getStyleClass().add("utilisateur-hover");
    }

    /**
     * Méthode appelée lorsque la souris sort du bouton Utilisateur
     */
    @FXML
    void userExited(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("utilisateur-hover");
        bouton.getStyleClass().add("utilisateur-not-hover");
    }
    
    @FXML
    void boutonImporterPresser(ActionEvent event) {
        EchangeurDeVue.launchPopUp("vpui", "Importer");
        if (Modele.isParametrageInitialise()) {
            EchangeurDeVue.echangerAvec("h", false);
        }
    }
    
    @FXML
    void onDragDropped(DragEvent event) {
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
