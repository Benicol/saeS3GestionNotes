package controleur;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import modele.Modele;

/** 
 * Controleur de la vue "VueAide.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, jodie.monterde
 */
public class VuePopUpAideControleur {

    @FXML
    private Label utilisateur;

    @FXML
    private VBox listePrincipale;

    @FXML
    private VBox menu;

    @FXML
    private Text nbSemestreParcours;
    
    private static Document document;
    
    
    /**
     * Effectue les traitement suivant dans cette ordre : 
     * - Met à jour l'utilisateur
     * @throws Exception 
     */
    @FXML
    void initialize() throws Exception {
        // Met à jour l'utilisateur
        utilisateur.setText(Modele.getUtilisateur().getPseudo());
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
         // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            document = db.parse(new File("..\\vue\\ressources\\aide.xml"));
            document.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets)
     * Boutons utilisant cette méthode : 
     * - Reinitialiser
     * - Annuler (modification de modalitées)
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
     * Méthode appelé lors de la sortie de la souris dans un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets)
     * Boutons utilisant cette méthode : 
     * - Reinitialiser
     * - Annuler (modification de modalitées)
     */
    @FXML
    void secondaryButtonExited(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("secondary-button-hover");
        bouton.getStyleClass().add("secondary-button-not-hover");
    }

    @FXML
    void userEntered(MouseEvent event) {

    }

    @FXML
    void userExited(MouseEvent event) {

    }

    @FXML
    void utilisateurPresser(ActionEvent event) {

    }
    
    @FXML
    void retourMenuPrincipal(ActionEvent event) {
        EchangeurDeVue.getPopUpStage().close();
    }

}
