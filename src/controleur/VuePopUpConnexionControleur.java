/*
 * VuePopUpImporterController.java                                        9 nov 2023
 * IUT Rodez, pas de copyright
 */
package controleur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import modele.Modele;
import modele.OutilCryptographie;
import modele.OutilFichier;
import modele.OutilReseau;

/** 
 * Contrôleur de la vue vue.VueExporter.fxml
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class VuePopUpConnexionControleur {
    
    @FXML
    private Text texte;
    
    @FXML
    private Button boutonAnnuler;
    
    @FXML
    private ImageView boutonImageAnnuler;
    
    private Thread enAttente;
    
    private Thread serveurSocket;
    private ServerSocket serveur;
    private boolean transfertOk = false;
    
    /**
     * Effectue les traitement suivant dans cette ordre : 
     * @throws Exception 
     */
    @FXML
    void initialize() throws Exception {
        EchangeurDeVue.getPopUpStage().setOnCloseRequest((event) -> close());
        texte.setText("En attente de connexion");
        // Créez une tâche (Task) pour effectuer le travail en arrière-plan
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    try {
                        if (enAttente.isInterrupted()) {
                            return null;
                        }
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        return null;
                    }
                    // Mettez à jour l'interface utilisateur dans le thread de l'interface utilisateur
                    javafx.application.Platform.runLater(() -> {
                        texte.setText(texte.getText() + ".");
                        if (texte.getText().substring(texte.getText().length() - 4, texte.getText().length() - 1).equals("...")) {
                            texte.setText(texte.getText().substring(0,texte.getText().length() - 4));
                        }
                    });
                }
            }
        };
        enAttente = new Thread(task);
        enAttente.start();
        serveur = new ServerSocket(20232);
        serveurSocket = new Thread() {
            public void run() {
                try {
                    System.out.println("serveur started");
                    Socket client = serveur.accept();
                    handleConnection(client);
                } catch (IOException e) {
                    return;
                }
            }
        };
        serveurSocket.start();
    }
    
    private void handleConnection(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            BigInteger ga = new BigInteger(reader.readLine());
            BigInteger b = OutilCryptographie.genererAB();
            BigInteger gb = OutilCryptographie.getG().pow(b.intValue());
            writer.println(gb.toString());
            BigInteger cleCodee = new BigInteger(reader.readLine().replaceAll("\\\\n", "\n"));
            String cle = OutilCryptographie.decoderCle(cleCodee, ga, b);
            String donneesCrypte = reader.readLine().replaceAll("\\\\n", "\n");
            String decrypter = OutilCryptographie.decoder(cle, donneesCrypte);
            OutilFichier.ecrire("test2.txt", decrypter);
            enAttente.interrupt();
            texte.setText("Transmission Terminé !");
            Thread.sleep(5000);
            transfertOk = true;
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Méthode appelée lors du clic sur le bouton "afficher l'adresse IP"
     * Cette méthode permet simplement de changer le texte du bouton
     * en l'adresse IP de l'utilisateur
     */
    @FXML
    void afficherAdresseIPPresser(ActionEvent event) {        
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        if (bouton.getText().equals("AFFICHER L'ADRESSE IP")) {
            bouton.setText("ADRESSE: " + OutilReseau.getIp());
        } else {
            bouton.setText("AFFICHER L'ADRESSE IP");
        }
        
    }
    
    /**
     * Méthode appelée lors du clic sur le bouton "annuler"
     */
    @FXML
    void annulerPresser(ActionEvent event) {
        try {
            serveur.close();
        } catch (IOException e) {}
        enAttente.interrupt();
        if (transfertOk) {
            EchangeurDeVue.getPopUpStage().close();
        } else {
            EchangeurDeVue.echangerAvecPopUp("vpui", "importer");
        }
    }
    
    void close() {
        EchangeurDeVue.getPopUpStage().setOnCloseRequest(null);
        try {
            serveur.close();
        } catch (IOException e) {
        }
        enAttente.interrupt();
        EchangeurDeVue.echangerAvecPopUp("vpui", "importer");
    }
    
    /**
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'primary-button' (bouton plein violet) afin d'en changer le style
     * Boutons utilisant cette méthode : 
     * - Bouton "Importer"
     * - Bouton "Afficher l'asresse IP"
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
     * Méthode appelée lors de la sortie de la souris d'un bouton de 
     * style 'primary-button' (bouton plein violet) afin de remettre son style par 
     * défaut.
     * Boutons utilisant cette méthode : 
     * - Bouton "Importer"
     * - Bouton "Afficher l'adresse IP"
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
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets) afin
     * d'en changer le style
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
     * Méthode appelée lors de la sortie de la souris d'un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets) afin de 
     * remettre son style par défaut.
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
}
