package controleur;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.HashMap;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    
    private HashMap<Button,Element> infos = new HashMap<>();
    
    /** bouton Sélectionner dans le menu à gauche */
    private Button selected;
    
    
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

            document = db.parse(new File("src\\vue\\ressources\\aide.xml"));
            document.getDocumentElement().normalize();
            NodeList sections = document.getElementsByTagName("section");
            for (int i = 0; i < sections.getLength(); i++) {
                Node section = sections.item(i);
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(
                        EchangeurDeVue.getModule("MAM")));
                Parent root = fxmlloader.load();
                VBox vboxP = (VBox) root;
                VBox vbox = (VBox) 
                        ((HBox) vboxP.getChildren().get(0)).getChildren().get(0);
                Text text = (Text) vbox.getChildren().get(0);
                text.setText(
                        section.getAttributes().getNamedItem("nom").getNodeValue());
                VBox contenu = (VBox) vbox.getChildren().get(1);
                menu.getChildren().add(vboxP);
                NodeList parties = section.getChildNodes();
                for (int j = 0; j < parties.getLength(); j++) {
                    Node partie = parties.item(j);
                    if (partie.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) partie;
                        // Charge le module
                        fxmlloader = new FXMLLoader(getClass().getResource(
                                             EchangeurDeVue.getModule("MSMB")));
                        root = fxmlloader.load();
                        Button button = (Button) root;
                        // Configure le module
                        if (partie.getNodeName() == "partie") {
                            button.setText(eElement.getAttribute("nom"));
                            button.setOnMouseEntered(
                                    (event) -> sideNavButtonInactiveEntered(event));
                            button.setOnMouseExited(
                                    (event) -> sideNavButtonInactiveExited(event));
                            button.setOnAction((event) -> {
                                try {
                                    eltMenuSelectionner(button);
                                    menuPressed(button);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            infos.put(button, eElement);
                            contenu.getChildren().add(button);
                        }
                    }
                    
                    
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
    
    private void menuPressed(Button button) {
        viderZonePrincipale();
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(
                    EchangeurDeVue.getModule("MTS")));
            Parent root;
            root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Label label = (Label) hbox.getChildren().get(0);
            Element eElement = infos.get(button);
            label.setText(eElement.getAttribute("nom"));
            listePrincipale.getChildren().add(label);
            NodeList nListe = eElement.getChildNodes();
            String textContent = "";
            fxmlloader = new FXMLLoader(getClass().getResource(
                    EchangeurDeVue.getModule("MAT")));
            root = fxmlloader.load();
            VBox vbox = (VBox) root;
            for (int j = 0; j < nListe.getLength(); j++) {
                Node partie = nListe.item(j);
                if (partie.getNodeType() == Node.ELEMENT_NODE) {
                    Element currentElement = (Element) partie;
                    
                    if (currentElement.getNodeName() == "paragraphe") {
                        Label texte = new Label();
                        texte.getStyleClass().add("text-aide");
                        texte.setWrapText(true);
                        texte.setText(currentElement.getTextContent());
                        vbox.getChildren().add(texte);
                    } else if (currentElement.getNodeName() == "image") {
                        String chemin = currentElement.getAttribute("chemin");
                        String width = currentElement.getAttribute("width");
                        String height = currentElement.getAttribute("height");
                        ImageView imageView = new ImageView();
                        imageView.setPickOnBounds(true);
                        imageView.setPreserveRatio(true);
                        imageView.setFitHeight(Integer.parseInt(height));
                        imageView.setFitWidth(Integer.parseInt(width));
                        Image image = new Image(getClass().getResource(
                                chemin).toExternalForm());
                        imageView.setImage(image);
                        vbox.getChildren().add(imageView);
                    } else if (currentElement.getNodeName() == "titre") {
                        Label texte = new Label();
                        texte.getStyleClass().add("titre-aide");
                        texte.setWrapText(true);
                        texte.setText(currentElement.getTextContent());
                        vbox.getChildren().add(texte);
                    }
                }
            }
            listePrincipale.getChildren().add(vbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /* Vide la liste principale de tous ces éléments */
    private void viderZonePrincipale() {
        while (!listePrincipale.getChildren().isEmpty()) {
            listePrincipale.getChildren().remove(0);
        }
    }

    /* Change quel bouton est visuellement sélectionné dans le menu */
    private void eltMenuSelectionner(Button button) {
        if (selected != button) {
            if (selected != null) {
                selected.getStyleClass().remove("side-nav-element-active");
                selected.getStyleClass().remove("side-nav-element-active-not-hover");
                selected.getStyleClass().add("side-nav-element-inactive");
                selected.getStyleClass().add("side-nav-element-inactive-not-hover");
                selected.setOnMouseEntered((event) -> sideNavButtonInactiveEntered(event));
                selected.setOnMouseExited((event) -> sideNavButtonInactiveExited(event));
            }
            selected = button;
            selected.getStyleClass().remove("side-nav-element-inactive");
            if (selected.getStyleClass().contains("side-nav-element-inactive-hover")) {
                selected.getStyleClass().remove("side-nav-element-inactive-hover");
            }
            if (selected.getStyleClass().contains("side-nav-element-inactive-not-hover")) {
                selected.getStyleClass().remove("side-nav-element-inactive-not-hover");
            }
            selected.getStyleClass().add("side-nav-element-active");
            selected.getStyleClass().add("side-nav-element-active-not-hover");
            selected.setOnMouseEntered(null);
            selected.setOnMouseExited(null);
        }
    }
    
    /**
     * Méthode appelé lors de la sortie de la souris dans un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets)
     * Boutons utilisant cette méthode : 
     * - oeuil (Competences et afficher les moyennes)
     */
    @FXML
    void secondaryButtonWhiteExited(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("secondary-button-white-hover");
        bouton.getStyleClass().add("secondary-button-not-hover");
    }
    
    void sideNavButtonInactiveEntered(MouseEvent event) {
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("side-nav-element-inactive-not-hover");
        bouton.getStyleClass().add("side-nav-element-inactive-hover");
    }
    
    void sideNavButtonInactiveExited(MouseEvent event) {
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("side-nav-element-inactive-hover");
        bouton.getStyleClass().add("side-nav-element-inactive-not-hover");
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
