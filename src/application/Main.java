/*
 * Main.java                                                             11 nov 2023
 * IUT Rodez, pas de copyright
 */
package application;

import java.io.IOException;

import controleur.EchangeurDeVue;
import controleur.VueHomepageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modele.Modele;
import modele.OutilCSV;
import modele.OutilFichier;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Classe de main, point d'entrée de l'application
 * 
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        /*
         * Création d'un chargeur de code FXML et chargement de la vue de l'application
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

            // On définit les caractéristiques de la fenêtre et lui associe la scène
            primaryStage.setTitle("Grade Tracker");
            EchangeurDeVue.setSceneCourante(scene);
            primaryStage.getIcons().add(new Image("vue/ressources/icone_application.png"));
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
        String test = "coucou\n";
        System.out.println(test.length());
        System.out.println("|" + test.indexOf('\n') + "|");
        launch(args); // appelera la méthode start
    }
}
