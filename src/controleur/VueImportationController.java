package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

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
            String file = dragboard.getFiles().get(0).getAbsolutePath();
            System.out.println("Fichier déposé : " + file);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }
}
