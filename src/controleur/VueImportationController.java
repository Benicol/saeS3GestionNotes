/*
 * VueImportationController.java                                          9 nov 2023
 * IUT Rodez, pas de copyright
 */
package controleur;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.WindowEvent;
import modele.Modele;

/** 
 * Contrôleur de la vue vue.VueImportation.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, jodie.monterde
 */
public class VueImportationController {
    
    @FXML
    void initialize() {
        EchangeurDeVue.getPrimaryStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                File executer = new File("executer.txt");
                executer.delete();
            }
        });
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
        // Va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // Change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("utilisateur-not-hover");
        bouton.getStyleClass().add("utilisateur-hover");
    }

    /**
     * Méthode appelée lorsque la souris sort du bouton Utilisateur
     */
    @FXML
    void userExited(MouseEvent event) {
        // Va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // Change de classe dans le css pour rendre son style originel au bouton.
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
        try {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasFiles()) {
                // Récupére le fichier déposé
                String file = dragboard.getFiles().get(0).getPath();
                Modele.importer(file);
                success = true;
                
            }
            event.setDropCompleted(success);
            event.consume();
            if (success) {
                EchangeurDeVue.echangerAvec("h", false);
            }
        } catch (IllegalArgumentException e) {
            // Gestion de l'exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("L'importation a rencontrée un problème...");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
