/*
 * VuePopUpConnexionControleur.java                                       9 nov 2023
 * IUT Rodez, pas de copyright
 */
package controleur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modele.Modele;
import modele.OutilCSV;
import modele.OutilCryptographie;
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
    
    private Thread serveurThread;
    private ServerSocket serveur;
    private boolean transfertOk = false;
    
    /**
     * Effectue les traitements suivant dans cette ordre : 
     * TODO
     * @throws Exception 
     */
    @FXML
    void initialize() throws Exception {
        EchangeurDeVue.getPopUpStage().setOnCloseRequest((event) -> close());
        texte.setText("En attente de connexion");
        // Crée une tâche (Task) pour effectuer le travail en arrière-plan
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
                    // Met à jour l'interface utilisateur dans le thread de l'interface utilisateur
                    javafx.application.Platform.runLater(() -> {
                        texte.setText(texte.getText() + ".");
                        if (texte.getText().substring(
                                texte.getText().length() - 4,
                                texte.getText().length() - 1).equals("...")) {
                            texte.setText(texte.getText().substring(
                                    0,texte.getText().length() - 4));
                        }
                    });
                }
            }
        };
        Task<Void> task2 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    try {
                        Socket client = serveur.accept();
                        handleConnection(client);
                        transfertOk = true;
                        javafx.application.Platform.runLater(() -> {
                            texte.setText("Données valides ! Importation terminée !");
                            boutonAnnuler.setText("Retour à l'application");
                            Image image = new Image(getClass().getResource(
                                    "../vue/ressources/icone_retour.png").toExternalForm());
                            boutonImageAnnuler.setImage(image);
                        });
                    } catch (IOException e) {
                        return null;
                    } catch (IllegalArgumentException e) {
                        javafx.application.Platform.runLater(() -> {
                            texte.setText("Données invalides ! L'importation a échoué.");
                            boutonAnnuler.setText("Retour à l'importation");
                            Image image = new Image(getClass().getResource(
                                    "../vue/ressources/icone_retour.png").toExternalForm());
                            boutonImageAnnuler.setImage(image);
                        });
                    }
                    
                }
            }
        };
        enAttente = new Thread(task);
        enAttente.start();
        serveur = new ServerSocket(20232);
        serveurThread = new Thread(task2);
        serveurThread.start();
    }
    
    private void handleConnection(Socket clientSocket) throws IllegalArgumentException {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream()), true);
            int p = Integer.parseInt(reader.readLine());
            int g = Integer.parseInt(reader.readLine());
            BigInteger ga = new BigInteger(reader.readLine());
            int b = OutilCryptographie.genererAB(p);
            BigInteger bIG = new BigInteger(Integer.toString(g));
            BigInteger gb = bIG.pow(b);
            writer.println(gb.toString());
            BigInteger cleCodee = new BigInteger(
                    reader.readLine().replaceAll("‣․", "\n"));
            String cle = OutilCryptographie.decoderCle(cleCodee, ga, b, p);
            String donneesCrypte = reader.readLine().replaceAll("‣․", "\n");
            String decrypter = OutilCryptographie.decoder(cle, donneesCrypte);
            enAttente.interrupt();
            Modele.importerReseau(OutilCSV.formaterToDonnees(decrypter));
            clientSocket.close();
        } catch (IOException e) {
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
        // Va chercher le bouton précis que la souris a survolé
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
        if (transfertOk) { //transfertOk
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
        // Va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // Change de classe dans le css pour assombrir le bouton.
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
        // Va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // Change de classe dans le css pour rendre son style originel au bouton.
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
        // Va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // Change de classe dans le css pour assombrir le bouton.
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
        // Va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // Change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("secondary-button-hover");
        bouton.getStyleClass().add("secondary-button-not-hover");
    }
}
