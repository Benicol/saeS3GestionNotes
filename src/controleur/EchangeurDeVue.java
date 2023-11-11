/*
 * EchangeurDeVue.java                                     26/05/2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package controleur;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * Classe outil permettant de gérer le changement de la vue affichée par la
 * scene de l'application. 
 * @author Corinne Servieres, Benjamin Nicol, Jodie Monterde, 
 */
public class EchangeurDeVue {
    
    private static HashMap<String, String> vues = new HashMap<>();    
    /** TODO comment method role
     * @param codeVue 
     * @return le chemin vers le module
     */
    public static String getNomVue(String codeVue) {
        if (!vues.containsKey(codeVue)) {
            throw new IllegalArgumentException("Code vue " + codeVue + " invalide");
        }
        return vues.get(codeVue);
    }
    
    private static HashMap <String, String> modules = new HashMap<>();
    /** TODO comment method role
     * @param codeModule code du module chercher
     * @return le chemin vers le module
     */
    public static String getModule(String codeModule) {
        if (!modules.containsKey(codeModule)) {
            throw new IllegalArgumentException("Code vue " + modules + " invalide");
        }
        return modules.get(codeModule);
    }

    /**
     * Table de hachage
     */
    private static Map<String, Parent> cache;
    
    /** Scene courante, ou scène qui est associée à la fenêtre principale */
    private static Scene sceneCourante;
    private static Scene scenePopUp;
    
    private static Stage primaryStage;
    private static Stage popUpStage;
    
    // création de la table cache
    static {
        cache = new HashMap<>();
        /* VUES */
        vues.put("h", "..\\vue\\Homepage.fxml");
        /* MODULES */
        modules.put("ME", "..\\vue\\modules\\ModuleEnseignement.fxml");
        modules.put("MEval", "..\\vue\\modules\\ModuleEvaluation.fxml");
        modules.put("MEvalE", "..\\vue\\modules\\ModuleEvaluationEdition.fxml");
        modules.put("MR", "..\\vue\\modules\\ModuleRessource.fxml");
        modules.put("MREA", "..\\vue\\modules\\ModuleRessourceEditionAjouter.fxml");
        modules.put("MSMB", "..\\vue\\modules\\ModuleSideMenuButton.fxml");
        modules.put("MTC", "..\\vue\\modules\\ModuleTitreCompetence.fxml");
        modules.put("MTR", "..\\vue\\modules\\ModuleTitreRessource.fxml");
        modules.put("MTRE", "..\\vue\\modules\\ModuleTitreRessourceEdition.fxml");
        modules.put("MS", "..\\vue\\modules\\ModuleSae.fxml");
        modules.put("MTS", "..\\vue\\modules\\ModuleTitreSae.fxml");
        modules.put("MEvalV", "..\\vue\\modules\\ModuleEvaluationVide.fxml");
    }

    /**
     * Affecte à la sceneCourante la scène créée dans la méthode start, donc celle
     * associée à la fenêtre principale
     * @param nouvelleScene Scene à affecter
     */
    public static void setSceneCourante(Scene nouvelleScene) {
        sceneCourante = nouvelleScene;
    }
    
    /**
     * Affecte à la scenePopUp la scène créée dans la méthode start, donc celle
     * associée à une fenetre de pop up
     * @param nouvelleScene Scene à affecter
     */
    public static void setScenePopUp(Scene nouvelleScene) {
        scenePopUp = nouvelleScene;
    }
    
    /** @return valeur de primaryStage */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    /** @return valeur de primaryStage */
    public static Stage getPopUpStage() {
        return popUpStage;
    }

    /** @param primaryStage nouvelle valeur de primaryStage */
    public static void setPrimaryStage(Stage primaryStage) {
        EchangeurDeVue.primaryStage = primaryStage;
    }
    
    /** @param popUpStage nouvelle valeur de popUpStage */
    public static void setPopUpStage(Stage popUpStage) {
        EchangeurDeVue.popUpStage = popUpStage;
    }
    
    /**
     * Modifie la vue associée à la scène courante, pour qu'elle devienne celle dont
     * le code est donné en argument La scène courante doit avoir été initialisée
     * 
     * @param codeVue code de la vue à placer sur la scène courante
     * @param garderEnMemoire TODO
     */
    public static void echangerAvec(String codeVue, boolean garderEnMemoire) {
        if (sceneCourante == null) {

            // pas de scène courante : impossible de modifier sa vue
            throw new IllegalStateException("Echange de vue impossible. Pas de scène courante.");
        }

        try {
            Parent racine;
            if (cache.containsKey(codeVue)) {
                racine = cache.get(codeVue);
            } else {
                racine = FXMLLoader.load(EchangeurDeVue.class.getResource(getNomVue(codeVue)));
                if (garderEnMemoire) {
                    cache.put(codeVue, racine);
                }
            }
            sceneCourante.setRoot(racine);
        } catch (IOException erreur) {
            System.out.println("Echec du chargement de la vue de code " + codeVue + " => " + erreur.getMessage());
        }
    }
    
    /**
     * @param codeVue code de la vue à placer sur la scène courante
     */
    public static void supprimerCache(String codeVue) {
        if (cache.containsKey(codeVue)) {
            cache.remove(codeVue);
        }
    }
    
    /**
     * Modifie la vue associée à la scène de pop up, pour qu'elle devienne celle dont
     * le code est donné en argument La scène pop up seras initialisé si pas sséjà initialisé
     * 
     * @param codeVue code de la vue à placer sur la scène courante
     * @param garderEnMemoire TODO
     */
    public static void launchPopUp(String codeVue) {
        try {
            Parent racine = FXMLLoader.load(EchangeurDeVue.class.getResource(getNomVue(codeVue)));
            if (scenePopUp == null) {
                scenePopUp = new Scene(racine);
            } else {
                scenePopUp.setRoot(racine);
            }
            if (popUpStage == null) {
                popUpStage = new Stage();
                popUpStage.setScene(scenePopUp);
                popUpStage.initModality(Modality.APPLICATION_MODAL);
            }
            popUpStage.showAndWait();
        } catch (IOException erreur) {
            System.out.println("Echec du chargement de la vue de code " + codeVue + " => " + erreur.getMessage());
        }
    }

    
}