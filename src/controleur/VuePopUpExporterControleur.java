package controleur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modele.Modele;
import modele.OutilCSV;
import modele.OutilCryptographie;

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
    
    private static final int PORT = 20232;
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
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets)
     * Boutons utilisant cette méthode : 
     * - oeuil (Competences et afficher les moyennes)
     */
    @FXML
    void secondaryButtonWhiteEntered(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("secondary-button-not-hover");
        bouton.getStyleClass().add("secondary-button-white-hover");
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

    
    /**
     * Methode appeler lors du clic sur le bouton "Etablir une connexion"
     * @throws InterruptedException 
     */
    @FXML
    void etablirUneConnexionPresser(ActionEvent event) throws InterruptedException {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Socket socket = new Socket();
                    socket.setSoTimeout(1000);
                    socket.connect(new InetSocketAddress(adresseIpInput.getText(), PORT), 1000);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                    BigInteger a = OutilCryptographie.genererAB();
                    BigInteger ga = OutilCryptographie.getG().pow(a.intValue());
                    writer.println(ga.toString());
                    BigInteger gb = new BigInteger(reader.readLine());
                    String cle = OutilCryptographie.creerCleVigenere();
                    BigInteger cleCodee = OutilCryptographie.coderCle(cle, a, gb);
                    writer.println(cleCodee.toString().replaceAll("\n", "\\\\n"));
                    String donneesCrypte = OutilCryptographie.encoder(cle, OutilCSV.formaterToCSV(Modele.exporter(programme, modalites)));
                    writer.println(donneesCrypte);
                    socket.close();
                    // Mettez à jour l'interface utilisateur dans le thread de l'interface utilisateur
                    javafx.application.Platform.runLater(() -> {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Transfert réussi");
                        alert.setHeaderText("Données transférées!");
                        alert.setContentText("Verifiez que l'importation s'est bien passer sur l'autre ordinateur !");
                        alert.showAndWait();
                    });
                } catch (IOException e) {
                    javafx.application.Platform.runLater(() -> {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("IP ERROR");
                        alert.setHeaderText("aucune application n'attend des données à cette adresse");
                        alert.setContentText("Consultez l'aide pour plus d'informations sur comment transmettre vos données");
                        alert.showAndWait();
                    });
                }
                return null;
            }
        };
        new Thread(task).start();
    }
    
    /**
     * Methode appeler lors du clic sur le bouton "Modalités d'évaluation"
     */
    @FXML
    void boutonSelectionPresser(ActionEvent event) {
    	Button bouton = (Button) event.getSource();
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
    }
    private void onMouseSwitch(Button bouton, String type) {
        if (type.equals("primary")) {
            bouton.setOnMouseEntered((event) -> primaryButtonEntered(event));
            bouton.setOnMouseExited((event) -> primaryButtonExited(event));
        } else {
            bouton.setOnMouseEntered((event) -> secondaryButtonWhiteEntered(event));
            bouton.setOnMouseExited((event) -> secondaryButtonWhiteExited(event));
        }
    }
}