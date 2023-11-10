package application;
	
import java.io.IOException;

import controleur.EchangeurDeVue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
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
        FXMLLoader chargeurFXML = new FXMLLoader();
        chargeurFXML.setLocation(getClass().getResource("vueTemporaire.fxml"));
        Parent racine;
        try {
            racine = chargeurFXML.load();
            Scene scene = new Scene(racine);
            scene.getRoot().requestFocus();

            // on définit les caractéristiques de la fenêtre et lui associe la scène
            primaryStage.setTitle("temporaire");
            primaryStage.setHeight(700);
            primaryStage.setWidth(600);
            EchangeurDeVue.setSceneCourante(scene);
            primaryStage.setScene(scene);
            EchangeurDeVue.setPrimaryStage(primaryStage);
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
