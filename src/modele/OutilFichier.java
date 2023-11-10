/*
 * OutilFichier.java                                                      30/10/2023
 * IUT de Rodez, pas de copyright.
 */
package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe outil permettant de manipuler des fichiers soit dans le but de :
 * - lire le contenu du fichier
 * - écrire dans le fichier.
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class OutilFichier {
    
    /**
     * Methode qui permet de lire le contenu d'un fichier.
     * @param chemin le chemin d'accès au fichier à lire.
     * @return le contenu du fichier.
     */
    public static String lire(String chemin) {
        StringBuilder message = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(chemin);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String ligne = bufferedReader.readLine();
            while (ligne != null) {
                message.append(ligne).append("\n");
                ligne = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            return "Une erreur s'est produite lors de la lecture du fichier : " 
                   + "fichier introuvable";
        }
        return message.toString();
    }

    /**
     * Methode qui permet d'écrire un message dans un fichier.
     * @param chemin le chemin d'accès au fichier.
     * @param contenu le contenu à écrire dans le fichier.
     * @return true si l'écriture du message a pu s'exécuter, false sinon.
     */
    public static boolean ecrire(String chemin, String contenu) {
        try {
            FileWriter fileWriter = new FileWriter(chemin);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(contenu);
            bufferedWriter.close();

            return true; // L'écriture s'est déroulée avec succès
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de l'écriture du "
                               + "fichier : " + e.getMessage());
            return false; // Une erreur s'est produite lors de l'écriture du fichier
        }
    }

    /**
     * Vérifie que le fichier fourni existe et qu'il correspond à 
     * l'extension fournie.
     * @param chemin le chemin d'accès au fichier.
     * @param extension l'extension du fichier.
     * @return true si le fichier existe et a la bonne extension, false sinon
     */
    public static boolean verifierFichier(String chemin, String extension) {
        boolean resultat = true;
        File fichier = new File(chemin);
        if (!fichier.exists() || !chemin.endsWith(extension)) {
            return false;
        }
        return resultat;
    }
}
