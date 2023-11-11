package application;
	
import java.io.IOException;

import controleur.EchangeurDeVue;
import controleur.VueHomepageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modele.Modele;
import javafx.scene.Parent;
import javafx.scene.Scene;


/** TODO comment class responsibility (SRP)
 * @author benji
 *
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        /*
         * création d'un chargeur de code FXML et chargement de la vue de l'application
         */
        EchangeurDeVue.setPrimaryStage(primaryStage);
        FXMLLoader chargeurFXML = new FXMLLoader();
        if (Modele.isParametrageInitialise()) {
            chargeurFXML.setLocation(getClass().getResource(EchangeurDeVue.getNomVue("h")));
        } else {
            chargeurFXML.setLocation(getClass().getResource(EchangeurDeVue.getNomVue("i")));
        }
        Parent racine;
        try {
            racine = chargeurFXML.load();
            Scene scene = new Scene(racine);
            scene.getRoot().requestFocus();

            // on définit les caractéristiques de la fenêtre et lui associe la scène
            primaryStage.setTitle("Gestionnaire de note");
            EchangeurDeVue.setSceneCourante(scene);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Programme principal
     * 
     * @param args non utilisé
     */
    public static void main(String[] args) {
        launch(args); // appelera la méthode start
    }
}
