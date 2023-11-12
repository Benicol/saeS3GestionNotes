package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/** 
 * Controleur de la vue vue.VueExporter.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class VueExporterControleur {
    /** TexteField contenant l'adresse ip */
    @FXML
    private TextField adresseIpInput;
    @FXML
    private ImageView logoModalites;

    @FXML
    private ImageView logoProgramme;
    
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
     * - Bouton "Etablir une connexion"
     */
    @FXML
    void boutonPleinEntree(MouseEvent event) {
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
     * - Bouton "Etablir une connexion"
     */
    @FXML
    void boutonPleinSortie(MouseEvent event) {
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
    void boutonVideEntree(MouseEvent event) {
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
    void boutonVideSortie(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("secondary-button-hover");
        bouton.getStyleClass().add("secondary-button-not-hover");
    }

    
    /**
     * Methode appeler lors du clic sur le bouton "Etablir une connexion"
     */
    @FXML
    void etablirUneConnexionPresser(ActionEvent event) {
    	  Button bouton = (Button) event.getSource();
          
          bouton.setText("Connexion en cours");
    }
    /**
     * Methode appeler lors du clic sur le bouton "Modalités d'évaluation"
     */
    @FXML
    void modalitesEvaluationPresser(ActionEvent event) {
    	Button bouton = (Button) event.getSource();
        if(bouton.getStyleClass().equals("secondary-button")) {
        	Image image = new Image(getClass().getResource("../vue/ressources/remove_icone.png").toExternalForm());
        	bouton.getStyleClass().remove("secondary-button");
        	bouton.getStyleClass().remove("secondary-button-hover");
        	bouton.getStyleClass().add("primary-button");
        	bouton.getStyleClass().add("primary-button-hover");
        	
        	logoModalites.setImage(image);
        	bouton.setOnMouseEntered((event) -> boutonPleinEntree(event));
        	bouton.setOnMouseExited((event) -> boutonPleinEntree(event));
        	
        } else {
        	Image image = new Image(getClass().getResource("../vue/ressources/icone_plus_violet.png").toExternalForm());
        	bouton.getStyleClass().remove("primary-button-hover");
        	bouton.getStyleClass().add("secondary-button-hover");
        	logoModalites.setImage(image);
        	//bouton.setOnMouseEntered((event) -> boutonVideEntree(event));
        	//bouton.setOnMouseExited((event) -> boutonVideEntree(event));
        }
    }
    /**
     * Methode appeler lors du clic sur le bouton "Programme nationnal"
     */
    @FXML
    void programmeNationnalPresser(ActionEvent event) {
    	Button bouton = (Button) event.getSource();
        if(bouton.getStyleClass().equals("secondary-button-hover")) {
        	Image image = new Image(getClass().getResource("../vue/ressources/remove_icone.png").toExternalForm());
        	bouton.getStyleClass().remove("secondary-button-hover");
        	bouton.getStyleClass().add("primary-button-hover");
        	//bouton.setOnMouseEntered((event) -> boutonPleinEntree(event));
        	//bouton.setOnMouseExited((event) -> boutonPleinEntree(event));
        	logoProgramme.setImage(image);
        	
        }else {
        	Image image = new Image(getClass().getResource("../vue/ressources/icone_plus_violet.png").toExternalForm());
        	bouton.getStyleClass().remove("primary-button-hover");
        	bouton.getStyleClass().add("secondary-button-hover");
        	//bouton.setOnMouseEntered((event) -> boutonVideEntree(event));
        	//bouton.setOnMouseExited((event) -> boutonVideEntree(event));
        	logoProgramme.setImage(image);
        }
    }

}