package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import modele.Modele;

/** 
 * Controleur de la vue vue.Importation.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, jodie.monterde
 */
public class VueImportationController {

    @FXML
    private Button boutonUtilisateur;

    @FXML
    void utilisateurPresser(ActionEvent event) {

    }

    @FXML
    void utilisateurEntree(ActionEvent event) {

    }

    @FXML
    void utilisateurSortie(ActionEvent event) {

    }

    @FXML
    void importerPresser(ActionEvent event) {

    }

    @FXML
    void boutonPleinEntree(ActionEvent event) {

    }

    @FXML
    void boutonPleinSortie(ActionEvent event) {

    }

    @FXML
    void exporterPresser(ActionEvent event) {

    }

    @FXML
    void reinitialiserPresser(ActionEvent event) {

    }

    @FXML
    void boutonVideEntree(ActionEvent event) {

    }

    @FXML
    void boutonVideSortie(ActionEvent event) {

    }

    @FXML
    void onDragOver(DragEvent event) {
        // Vérifie si les données glissées sont des fichiers
        if (event.getDragboard().hasFiles()) {
            // Accepte le mode de transfert comme une copie
            event.acceptTransferModes(TransferMode.COPY);
        }
        // Indique que l'événement a été géré
        event.consume();
    }

    @FXML
    void onDragDropped(DragEvent event) {
        // Récupère les données glissées
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        
        // Vérifie si les données glissées sont des fichiers
        if (dragboard.hasFiles()) {
            success = true;
            
            // Récupère le chemin du premier fichier
            String file = dragboard.getFiles().get(0).getAbsolutePath();
            try {
                // Tente d'importer le fichier
                Modele.importer(file);
                
                // Affiche une boîte de dialogue d'information en cas de succès
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Test");
                alert.setHeaderText("Paramétrage effectué");
                alert.setContentText("L'importation s'est déroulée sans accroc !");
                alert.showAndWait();
                
                // Change la vue après l'importation
                EchangeurDeVue.echangerAvec("h", false);
            } catch (IllegalArgumentException e) {
                /* 
                 * En cas d'échec, affiche une boîte de dialogue d'erreur 
                 * avec le message d'erreur spécifique
                 */
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("L'importation a rencontré un problème...");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
        // Indique si le lâcher a été complété avec succès
        event.setDropCompleted(success);
        // Indique que l'événement a été géré
        event.consume();
    }
}
