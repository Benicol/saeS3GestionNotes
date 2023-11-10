/*
 * Modele.java                                                            9 nov 2023
 * IUT de Rodez, pas de droit d'auteur 
 */
package modele;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Modele.java                                                                                 21/10/2015
 * No copyright. 
=======
 * Classe représentant le modèle de l'application (gérée selon un modèle MVC).
 * Responsabilités principales :
 *     - Gérer la sauvegarde et le chargement des données.
 *     - Importer des données depuis un fichier CSV.
 *     - Vérifier la validité du format des données.
 *     - Fournir des getters pour accéder aux paramètres et données.
 *     - Gérer l'initialisation et la réinitialisation du paramétrage.
 *     - Gérer les opérations liées à la classe Utilisateur.
 *     - Gérer les opérations liées à la classe Parametrage.
 *     - Fournir des méthodes utilitaires pour vérifier des chaînes.
 *     - Gérer l'initialisation et l'accès à l'adresse IP de l'ordinateur.
 *     - Contient un bloc statique pour l'initialisation des variables statiques.
 *     - Utilise la sérialisation pour sauvegarder et charger les données.
 *     - Gère les exceptions liées à l'entrée/sortie et à la classe non trouvée.
 *     - Utilise différentes classes auxiliaires pour des opérations spécifiques.
 * 
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class Modele {
    
    /* Déclaration de variables statiques pour le paramétrage et l'utilisateur */
    private static Parametrage parametrage;
    private static Utilisateur utilisateur;
    
    /* Bloc statique se lançant au démarrage de l'application pour l'initialiser */
    static {
        parametrage = null;
        utilisateur = new Utilisateur();
        charger();
    }
    
    /**
     * Méthode permettant de réinitialiser le paramétrage en le mettant à null.
     */
    public static void reset() {
        parametrage = null;
        sauvegarder();
    } 
    
    /**
     * Méthode permettant de sauvegarder les données (utilisateur et paramétrage) 
     * à l'aide de la sérialisation.
     */
    public static void sauvegarder() {
        try {
            ObjectOutputStream fluxEcriture = 
                    new ObjectOutputStream(new FileOutputStream("donnees.bin"));
            fluxEcriture.writeObject(utilisateur);
            fluxEcriture.writeObject(parametrage);
            fluxEcriture.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des données "
                               + "(IOExeption)");
        }
    }
    
    /**
     * Méthode permettant de charger les données sauvegardées depuis 
     * le fichier "donnees.bin".
     */
    public static void charger() {
        try {
            if (OutilFichier.verifierFichier("donnees.bin", ".bin")) {
                ObjectInputStream fluxLecture = 
                        new ObjectInputStream(new FileInputStream("donnees.bin"));
                utilisateur = (Utilisateur) fluxLecture.readObject();
                parametrage = (Parametrage) fluxLecture.readObject();
                fluxLecture.close();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des données "
                               + "(IOExeption)");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement des données "
                               + "(ClassNotFoundException)");
        }
    }
    
    /**
     * Méthode permettant d'importer les informations depuis 
     * un fichier CSV spécifié.
     * @param chemin Chemin du fichier CSV à importer.
     * @throws IllegalArgumentException Si le paramétrage est déjà initialisé, si 
     * le fichier n'existe pas, ou si le fichier CSV ne respecte pas le format
     * attendu.
     */
    public static void importer(String chemin) {
        
        /* Déclaration du tableau bidimensionnel qui contiendra les données du CSV */
        String[][] donnees = null;
        
        /* Vérifie si le paramétrage est déjà initialisé */
        if (isParametrageInitialise()) {
            throw new IllegalArgumentException("L'application contient déjà des données!");
        }
        
        /* Vérifie l'existence du fichier CSV */
        if (OutilFichier.verifierFichier(chemin, ".csv")) {
            /* Formate les données du CSV */
            donnees = OutilCSV.formaterToDonnees(OutilFichier.lire(chemin));
        } else {
            throw new IllegalArgumentException("Le fichier n'existe pas");
        }
        
        /* Vérifie si le format des données du CSV est valide */
        if (donnees != null && verifierFormatDonnees(donnees)) {
            /* Déclaration des variables pour stocker les informations du CSV */
            String semestre;
            String parcours;
            HashMap<String, String> listeSae = new HashMap<>();
            HashMap<String, String> listeRessource = new HashMap<>();
            ArrayList<String[][]> listeCompetence = new ArrayList<>();
            HashMap<String,ArrayList<Evaluation>> ressource = new HashMap<>();
            
            /* Récupère le semestre et le parcours depuis les 1e lignes du CSV */
            semestre = donnees[1][1];
            parcours = donnees[2][1];
            
            /* Parcours des lignes du CSV à partir de la troisième ligne */
            for (int i = 3; i < donnees.length; i++) {
                /* Si la ligne concerne une compétence */
                if (donnees[i].length >= 1 && donnees[i][0].equals("Compétence")) {
                    ArrayList<String[]> competence = new ArrayList<>();;
                    String[] infoLigne = new String[2];
                    infoLigne[0] = donnees[i][1];
                    infoLigne[1] = donnees[i][2];
                    System.out.println(infoLigne[0] + "|" + infoLigne[1]);
                    competence.add(infoLigne.clone());
                    int poids = 0;
                    i++;
                    
                    /* Parcours des lignes suivantes pour récupérer les informations
                     * (ressources et saé) liés à la compétence
                     */
                    while (poids != 100) {
                        i++;
                        infoLigne[0] = donnees[i][1];
                        infoLigne[1] = donnees[i][3];
                        /* Vérifie si la ligne concerne une ressource ou une SAE */
                        if (donnees[i][0].equals("Ressource") 
                                && !listeRessource.containsKey(infoLigne[0])) {
                            listeRessource.put(infoLigne[0], donnees[i][2]);
                        } else if (!donnees[i][0].equals("Ressource") 
                                && !listeSae.containsKey(infoLigne[0])) {
                            listeSae.put(infoLigne[0], donnees[i][2]);
                        }
                        
                        competence.add(infoLigne.clone());
                        poids += Integer.parseInt(donnees[i][3]);
                    }
                    /* Ajoute la compétence à la liste des compétences */
                    listeCompetence.add(competence.toArray(new String[0][0]));
                } else if (donnees[i].length >= 1 // Si la ligne concerne une ressource
                        && donnees[i][0].equals("Ressource")) { 
                    
                    ArrayList<Evaluation> listeEvaluation = new ArrayList<>();
                    String[] infoEvaluation = new String[3];
                    String key = donnees[i][1];
                    int poids = 0;
                    i++;
                    
                    /* Parcours des lignes suivantes pour récupérer les évaluations 
                     * de la ressource */
                    while (poids != 100) {
                        i++;
                        infoEvaluation[0] = donnees[i][0];
                        infoEvaluation[1] = donnees[i][1];
                        infoEvaluation[2] = donnees[i][2];
                        
                        poids += Integer.parseInt(donnees[i][2]);
                        listeEvaluation.add(new Evaluation(infoEvaluation[0],
                                Double.parseDouble(infoEvaluation[2]) / 100, 
                                infoEvaluation[1]));
                    }
                    /* Ajoute les évaluations de la ressource à la liste des ressources */
                    ressource.put(key, listeEvaluation);
                }
            }
            
            /* Crée un nouveau paramétrage avec les informations récupérées */
            parametrage = new Parametrage(semestre, parcours, 
                    listeCompetence.toArray(new String[0][0][0]) , 
                    listeSae, listeRessource);
            
            /* Ajoute les évaluations des ressources au paramétrage */
            for (String key : ressource.keySet()) {
                for (Evaluation evaluation : ressource.get(key)) {
                    parametrage.getListeRessources().get(key).ajouterEvaluation(evaluation);
                }
            }
        } else {
            throw new IllegalArgumentException("Le fichier n'est pas valide");
        }
        sauvegarder();
    }
    
    /**
     * Vérifie si les données fournies dans le CSV sont correctes ou non
     * @param donnees sous formes de tableau bidimensionnel 
     * (utilisation de la méthode formaterToDonnees de OutilCSV)
     * @return true si les donnees sont valide, false sinon
     */
    public static boolean verifierFormatDonnees(String[][] donnees) {
        /* Vérifie la validité des données semestre et parcours en 2d et 3e ligne. */
        boolean donneesValide = donnees[1].length >= 2 
                             && donnees[1][0].equals("Semestre") 
                             && isNumeric(donnees[1][1])
                             && donnees[2].length >= 2 
                             && donnees[2][0].equals("Parcours") 
                             && donnees[2][1].length() != 0;
                             
        /* Scanne chaque ligne de la suite du tableau à la recherche de "Compétence"
         *  ou "Ressource" */
        for (int i = 3; i < donnees.length && donneesValide; i++) {
            // Si la ligne commence par "Compétence"
            if (donnees[i].length >= 1 && donnees[i][0].equals("Compétence")) {
                // Vérifie la validité de la ligne avec "Compétence"
                donneesValide = donnees[i].length >= 3 && isIdentifiant(donnees[i][1]) && donnees[i][2].length() != 0;
                if (donneesValide) {
                    i++; // Passe la ligne suivante avec les intitulés des colonnes
                }
                int poids = 0; // poids total des ressources
                while (poids != 100 && donneesValide 
                        && i < donnees.length 
                        && donnees[i].length >= 4) { // Tant que poids total != 100
                    i++; // Passe à la ligne suivante
                    
                    /* Vérifie si la composition de la ligne est bien : 
                     * 1. type d'évaluation, 2. identifiant, 3. libellé, 4. poids
                     */
                    donneesValide = donnees[i][0].matches("[A-Z][A-Za-z]*");
                    donneesValide = donneesValide && isIdentifiant(donnees[i][1]);
                    donneesValide = donneesValide && donnees[i][2].length() != 0;
                    donneesValide = donneesValide && isNumeric(donnees[i][3]);
                    
                    /* Si la ligne est valide, ajoute le poids de l'évaluation 
                     * au poids total
                     */
                    if (donneesValide) {
                        poids += Integer.parseInt(donnees[i][3]);
                    }
                }
                // donneesValide est valide si le poids total est égal à 100 seulement.
                donneesValide = poids == 100;
            // Si la ligne commence par "Ressource"
            } else if (donnees[i].length >= 1 && donnees[i][0].equals("Ressource")){
                // Vérifie la validité de la ligne avec "Ressource"
                donneesValide = donnees[i].length >= 3 
                        && isIdentifiant(donnees[i][1]) 
                        && donnees[i][2].length() != 0;
                if (donneesValide) {
                    i++; // Passe la ligne suivante avec les intitulés des colonnes
                }
                int poids = 0; // Poids total des évaluations
                while (poids != 100 && donneesValide && i < donnees.length 
                        && donnees[i].length >= 3) {
                    i++; // Passe à la ligne suivante
                    
                    /* Vérifie si la composition de la ligne est bien :
                     * 1. Type d'évaluation, 2. date, 3. poids
                     */
                    donneesValide = donnees[i][0].length() != 0;
                    donneesValide = donneesValide && isNumeric(donnees[i][2]);
                    
                    // Si la ligne est valide, ajoute le poids de l'évaluation au poids total
                    if (donneesValide) {
                        poids += Integer.parseInt(donnees[i][2]);
                    }
                }
            // donneesValide est valide si le poids total est égal à 100 seulement.
            donneesValide = poids == 100;
            } else {
                donneesValide = donnees[i].length == 0;
            }
        }
        return donneesValide;
    }

    /* Méthode utilitaire pour vérifier si une chaîne est composés de chiffres */
    private static boolean isNumeric(String str) {
        boolean valide;
        if (str == null || str.length() == 0) {
            valide = false;
        } else {
            valide = str.matches("\\d+");
        }
        return valide;
    }

    /* Méthode utilitaire pour vérifier si une chaîne est un identifiant (X1.01) */
    private static boolean isIdentifiant(String str) {
        boolean valide;
        if (str == null || str.length() == 0) {
            valide =  false;
        } else if (str.charAt(0) == 'U'){
            valide = str.matches("[A-Z]\\d.\\d");
        } else {
            valide = str.matches("[A-Z]\\d\\.\\d{2}");
        }
        return valide;
    }
    
    /**
     * Getter de listeRessource présent dans paramétrage
     * @return listeRessources de paramétrage
     */
    public static HashMap<String, Ressource> getRessources() {
        return parametrage.getListeRessources();
    }
    
    /**
     * Getter de listeCompétences présent dans paramétrage
     * @return listeCompétences de paramétrage
     */
    public static HashMap<String, Competence> getCompetences() {
      
        return parametrage.getListeCompetences();
    }
    
    /**
     * Getter de l'adresse ip de l'ordinateur 
     * @return adresseIp présent dans outilReseaux
     */
    public static String getIp() {
        return OutilReseau.getIp(); 
    }
    /**
     * Getter de paramétrage
     * @return paramétrage
     */
    public static Parametrage getParametrage() {
        return parametrage;
    }
    
    /**
     * Getter de listeSae présent dans paramétrage
     * @return listeSae de paramétrage
     */
    public static HashMap<String, Sae> getSae() {
        return parametrage.getListeSaes();
    }
    
    /**
     * Getter de l'utilisateur
     * @return utilisateur 
     */
    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }
     /**
      * setter du parametrage (A UTILISE UNIQUEMENT POUR LES TESTS)
     * @param parametre le parametrage a mettre
      */
    public static void setParametrage(Parametrage parametre) {
        parametrage = parametre;
    }

    /**
     * Test si le paramétrage est initialisé
     * @return true si le parametrage n'est aps null, false sinon
     */
    public static boolean isParametrageInitialise() {
        return parametrage != null;
    }
}
