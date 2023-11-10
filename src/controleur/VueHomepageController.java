package controleur;

import modele.Competence;
import modele.Modele;
import modele.Ressource;
import modele.Sae;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

/** 
 * Controleur de la vue vue.Homepage.fxml
 */
public class VueHomepageController {
    /** Bouton invisible contenant le nom de l'utilisateur*/
    @FXML
    private Button boutonUtilisateur;
    /** listePrincipal, VBox central de l'application */
    @FXML
    private VBox listePrincipal;
    /** liste des ressources à gauche */
    @FXML
    private VBox listeRessources;
    /** liste des saes à gauche */
    @FXML
    private VBox listeSaes;
    /** liste des competences à gauche */
    @FXML
    private VBox listesCompetences;
    /**
     * Effectue les traitement suivant dans cette ordre : 
     * - si le parametrage = null, alors lance la vue importer
     * - Met en grand la fenetre
     * - Met à jour l'utilisateur
     * - affiche les competences
     * - affiche les ressources
     * - affiche les saes
     * @throws Exception 
     * 
     */
    @FXML
    void initialize() throws Exception {
        // si le parametrage = null, alors lance la vue importer
        if (Modele.getParametrage() == null) {
            System.err.println("LANCE LA VUE PAS FAITE LA");
        }
        // Met en grand la fenetre
        EchangeurDeVue.getPrimaryStage().setMaximized(true);
        // Met à jour l'utilisateur
        boutonUtilisateur.setText(Modele.getUtilisateur().getPseudo());
        // affiche les competences
        setVBoxListeCompetence();
        // affiche les ressources
        setVBoxListeRessouces();
        // affiche les saes
        setVBoxListeSaes();
    }
    
    private void setVBoxListeCompetence() throws Exception {
        String[] keys = Modele.getCompetences().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                button.setOnAction((event) -> {
                    try {
                        competenceCliquer(Modele.getCompetences().get(key));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
                button.setText(Modele.getCompetences().get(key).creerIntitule());
                listesCompetences.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (1) : " + e.getMessage());
            }
        }
    }
    
    private void setVBoxListeRessouces() throws Exception {
        String[] keys = Modele.getRessources().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                button.setOnAction((event) -> ressourceCliquer(Modele.getRessources().get(key)));
                button.setText(Modele.getRessources().get(key).creerIntitule());
                listeRessources.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (2) : " + e.getMessage());
            }
        }
    }

    private void setVBoxListeSaes() throws Exception {
        String[] keys = Modele.getSae().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                button.setOnAction((event) -> saeCliquer(Modele.getSae().get(key)));
                button.setText(Modele.getSae().get(key).creerIntitule());
                listeSaes.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (3) : " + e.getMessage());
            }
        }
    }

    @FXML
    void afficherMesMoyennesPresser(ActionEvent event) {
    }

    @FXML
    void boutonPleinEntree(MouseEvent event) {

    }

    @FXML
    void boutonPleinSortie(MouseEvent event) {

    }

    @FXML
    void boutonVideEntree(MouseEvent event) {

    }

    @FXML
    void boutonVideSortie(MouseEvent event) {

    }

    @FXML
    void exporterPresser(ActionEvent event) {

    }

    @FXML
    void importerPresser(ActionEvent event) {

    }

    @FXML
    void reinitialiserPresser(ActionEvent event) {

    }

    @FXML
    void utilisateurEntree(MouseEvent event) {

    }

    @FXML
    void utilisateurPresser(ActionEvent event) {

    }

    @FXML
    void utilisateurSortie(MouseEvent event) {

    }
    
    private void competenceCliquer(Competence competence) throws Exception {
        System.out.println(competence.getLibelle());
        viderListePrincipal();
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTC")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Text titre = ((Text) ((HBox) hbox.getChildren().get(0)).getChildren().get(0));
            Text note = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(0);
            Text diviseur = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(1);
            titre.setText(competence.creerIntitule());
            if (competence.isCalculable()) {
                note.setText(competence.calculerMoyenne().toString()); 
            } else {
                note.setText("Moyenne indisponible");
                diviseur.setText("");
            }
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (4) : " + e.getMessage());
        }
    }
    
    private void ressourceCliquer(Ressource ressource) {
        System.out.println(ressource.getLibelle());
        viderListePrincipal();
    }
    
    private void saeCliquer(Sae sae) {
        System.out.println(sae.getLibelle());
        viderListePrincipal();
    }
    
    private void viderListePrincipal() {
        while (!listePrincipal.getChildren().isEmpty()) {
            listePrincipal.getChildren().remove(0);
        }
    }

}
