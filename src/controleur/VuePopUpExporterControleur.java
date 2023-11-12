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
public class VuePopUpExporterControleur {
    /** TexteField contenant l'adresse ip */
    @FXML
    private TextField adresseIpInput;
    @FXML
    private ImageView logoModalites;

    @FXML
    private ImageView logoProgramme;
    
    private boolean modalites = false;
    private boolean programme = false;
    
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
     */
    @FXML
    void etablirUneConnexionPresser(ActionEvent event) {
    	  Button bouton = (Button) event.getSource();
          
          bouton.setText("Implémentation en cours");
    }
    /**
     * Methode appeler lors du clic sur le bouton "Modalités d'évaluation"
     */
    @FXML
    void boutonSelectionPresser(ActionEvent event) {
    	Button bouton = (Button) event.getSource();
    	System.out.println(bouton.getId());
    	Image image;
        if(bouton.getStyleClass().contains("secondary-button")) {
        	image = new Image(getClass().getResource("../vue/ressources/icone_moins.png").toExternalForm());
        	bouton.getStyleClass().remove("secondary-button");
        	bouton.getStyleClass().remove("secondary-button-hover");
        	bouton.getStyleClass().add("primary-button");
        	bouton.getStyleClass().add("primary-button-hover");
        	onMouseSwitch(bouton, "primary");
        	
        } else {
        	image = new Image(getClass().getResource("../vue/ressources/icone_plus.png").toExternalForm());
        	bouton.getStyleClass().remove("primary-button");
        	bouton.getStyleClass().remove("primary-button-hover");
        	bouton.getStyleClass().add("secondary-button");
        	bouton.getStyleClass().add("secondary-button-hover");
        	onMouseSwitch(bouton, "secondary");
        }
        if (bouton.getId().equals("programmeNational")) {
            programme = !programme;
            logoProgramme.setImage(image);
        } else {
            modalites = !modalites;
            logoModalites.setImage(image);
        }
        System.out.println("programme : " + programme);
        System.out.println("modalites : " + modalites);
    }
    private void onMouseSwitch(Button bouton, String type) {
        if (type.equals("primary")) {
            bouton.setOnMouseEntered((event) -> primaryButtonEntered(event));
            bouton.setOnMouseExited((event) -> primaryButtonExited(event));
        } else {
            bouton.setOnMouseEntered((event) -> secondaryButtonEntered(event));
            bouton.setOnMouseExited((event) -> secondaryButtonExited(event));
        }
    }
}