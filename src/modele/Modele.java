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
 */
public class Modele {
	
	
    private static Parametrage parametrage;
    private static Utilisateur utilisateur;
    
    static {
        parametrage = null;
        utilisateur = new Utilisateur();
        charger();
    }
    /**
     * Reset paramétrage (le met à null)
     */
    public static void reset() {
        parametrage = null;
    } 
    
    /**
     * Sauvegarde à l'aide de la classe Serializable
     * L'utilisateur puis le parametrage
     */
    public static void sauvegarder() {
        try {
            ObjectOutputStream fluxEcriture = new ObjectOutputStream(new FileOutputStream("donnees.bin"));
            fluxEcriture.writeObject(utilisateur);
            fluxEcriture.writeObject(parametrage);
            fluxEcriture.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des donnees (IOExeption)");
        }
    }
    /**
     * Charge les données sauvegardées
     * 
     */
    public static void charger() {
        try {
            if (OutilFichier.verifierFichier("donnees.bin", ".bin")) {
                ObjectInputStream fluxLecture = new ObjectInputStream(new FileInputStream("donnees.bin"));
                utilisateur = (Utilisateur) fluxLecture.readObject();
                parametrage = (Parametrage) fluxLecture.readObject();
                fluxLecture.close();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des donnees (IOExeption)");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement des donnees (ClassNotFoundException)");
        }
    }
    /**
     * Importe les informations du csv mis en parametre
     * @param chemin fichier CSV fournis
     */
    public static void importer(String chemin) {
        String donneesCSV = null;
        String[][] donnees = null;
        if (isParametrageInitialise()) {
            throw new IllegalArgumentException("L'application contient déjà des données!");
        }
        if (OutilFichier.verifierFichier(chemin, ".csv")) {
            donneesCSV = OutilFichier.lire(chemin);
            donnees = OutilCSV.formaterToDonnees(donneesCSV);
        } else {
            throw new IllegalArgumentException("Le fichier n'existe pas");
        }
        if (donnees != null && verifierFormatDonnees(donnees)) {
            String semestre;
            String parcours;
            HashMap<String, String> listeSae = new HashMap<>();
            HashMap<String, String> listeRessource = new HashMap<>();
            ArrayList<String[][]> listeCompetence = new ArrayList<>();
            HashMap<String,ArrayList<Evaluation>> ressource = new HashMap<>();
            semestre = donnees[1][1];
            parcours = donnees[2][1];
            for (int i = 3; i < donnees.length; i++) {
                if (donnees[i].length >= 1 && donnees[i][0].equals("Compétence")) {
                    ArrayList<String[]> competence = new ArrayList<>();;
                    String[] infoLigne = new String[2];
                    infoLigne[0] = donnees[i][1];
                    infoLigne[1] = donnees[i][2];
                    System.out.println(infoLigne[0] + "|" + infoLigne[1]);
                    competence.add(infoLigne.clone());
                    int poids = 0;
                    i++;
                    while (poids != 100) {
                        i++;
                        infoLigne[0] = donnees[i][1];
                        infoLigne[1] = donnees[i][3];
                        if (donnees[i][0].equals("Ressource") && !listeRessource.containsKey(infoLigne[0])) {
                            listeRessource.put(infoLigne[0], donnees[i][2]);
                        } else if (!donnees[i][0].equals("Ressource") && !listeSae.containsKey(infoLigne[0])) {
                            listeSae.put(infoLigne[0], donnees[i][2]);
                        }
                        competence.add(infoLigne.clone());
                        poids += Integer.parseInt(donnees[i][3]);
                    }
                    listeCompetence.add(competence.toArray(new String[0][0]));
                } else if (donnees[i].length >= 1 && donnees[i][0].equals("Ressource")) {
                    ArrayList<Evaluation> listeEvaluation = new ArrayList<>();
                    String[] infoEvaluation = new String[3];
                    String key = donnees[i][1];
                    int poids = 0;
                    i++;
                    while (poids != 100) {
                        i++;
                        infoEvaluation[0] = donnees[i][0];
                        infoEvaluation[1] = donnees[i][1];
                        infoEvaluation[2] = donnees[i][2];
                        
                        poids += Integer.parseInt(donnees[i][2]);
                        listeEvaluation.add(new Evaluation(infoEvaluation[0],Double.parseDouble(infoEvaluation[2]) / 100, infoEvaluation[1]));
                    }
                    ressource.put(key, listeEvaluation);
                }
            }
            parametrage = new Parametrage(semestre, parcours, listeCompetence.toArray(new String[0][0][0]) , listeSae, listeRessource);
            for (String key : ressource.keySet()) {
                for (Evaluation evaluation : ressource.get(key)) {
                    parametrage.getListeRessources().get(key).ajouterEvaluation(evaluation);
                }
            }
        } else {
            throw new IllegalArgumentException("Le fichier n'est pas valide");
        }

    }
    /** verifie si les donnees founis dans le csv sont correcte ou non
     * @param donnees sous formes de tableau de tableau (utiliser la methode formaterToDonnees de OutilCSV)
     * @return true si les donnees sont valide, false sinon
     */
    public static boolean verifierFormatDonnees(String[][] donnees) {
        //verifie la validitre des donnees semestre et parcours en seconde et troisieme ligne.
        boolean donneesValide = donnees[1].length >= 2 && donnees[1][0].equals("Semestre") && isNumeric(donnees[1][1]);
        donneesValide = donnees[2].length >= 2 && donneesValide && donnees[2][0].equals("Parcours") && donnees[2][1].length() != 0;
        //Scan chaque ligne de la suite du tableau à la recherche de "Compétence" ou "Ressource"
        for (int i = 3; i < donnees.length && donneesValide; i++) {
            //Si la ligne commence par "Compétence"
            if (donnees[i].length >= 1 && donnees[i][0].equals("Compétence")) {
                //verifie la validite de la ligne avec "Compétence"
                donneesValide = donnees[i].length >= 3 && isIdentifiant(donnees[i][1]) && donnees[i][2].length() != 0;
                if (donneesValide) {
                    i++; //passe la ligne suivante avec les intitules des colonnes
                }
                int poids = 0; // poid total des ressources
                while (poids != 100 && donneesValide && i < donnees.length && donnees[i].length >= 4) { //tant que le poid total n'est pas égal à 100
                    i++; //passe à la ligne suivante
                    // verifie si la composition de la ligne est bien type d'évaluation, identifiant, libelé et poid
                    donneesValide = donnees[i][0].matches("[A-Z][A-Za-z]*");
                    donneesValide = donneesValide && isIdentifiant(donnees[i][1]);
                    donneesValide = donneesValide && donnees[i][2].length() != 0;
                    donneesValide = donneesValide && isNumeric(donnees[i][3]);
                    // si la ligne est valide, ajoute le poid de l'évaluation au poid total
                    if (donneesValide) {
                        poids += Integer.parseInt(donnees[i][3]);
                    }
                }
                // donneesValide est valide si le poid total est égal à 100 seulment.
                donneesValide = poids == 100;
            //Si la ligne commence par "Ressource"
            } else if (donnees[i].length >= 1 && donnees[i][0].equals("Ressource")){
                //verifie la validite de la ligne avec "Ressource"
                donneesValide = donnees[i].length >= 3 && isIdentifiant(donnees[i][1]) && donnees[i][2].length() != 0;
                if (donneesValide) {
                    i++; //passe la ligne suivante avec les intitules des colonnes
                }
                int poids = 0; // poid total des evaluations
                while (poids != 100 && donneesValide && i < donnees.length && donnees[i].length >= 3) {
                    i++; //passe à la ligne suivante
                    // verifie si la composition de la ligne est bien Type d'évaluation, date et poid
                    donneesValide = donnees[i][0].length() != 0;
                    donneesValide = donneesValide && isNumeric(donnees[i][2]);
                    // si la ligne est valide, ajoute le poid de l'évaluation au poid total
                    if (donneesValide) {
                        poids += Integer.parseInt(donnees[i][2]);
                    }
                }
            // donneesValide est valide si le poid total est égal à 100 seulment.
            donneesValide = poids == 100;
            } else {
                donneesValide = donnees[i].length == 0;
            }
        }
        return donneesValide;
    }

    private static boolean isNumeric(String str) {
        boolean valide;
        if (str == null || str.length() == 0) {
            valide = false;
        } else {
            valide = str.matches("\\d+");
        }
        return valide;
    }

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
     * Getter de paramétrage
     * @return paramétrage
     */
    public static Parametrage getParametrage() {
        return parametrage;
    }
    
    /**
     * Getter de listeCompétences présent dans paramétrage
     * @return listeCompétences de paramétrage
     */
    public static HashMap<String, Competence> getCompetences() {
      
        return parametrage.getListeCompetences();
    }
    
    /**
     * Getter de listeSae présent dans paramétrage
     * @return listeSae de paramétrage
     */
    public static HashMap<String, Sae> getSae() {
        return parametrage.getListeSaes();
    }
    
    /**
     * Getter de l'utilisateur et si utilisateur est null 
     * initialise utilisateur
     * @return utilisateur 
     */
    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    /**
     * Getter de l'adresse ip de l'ordinateur 
     * @return adresseIp présent dans outilReseaux
     */
    public static String getIp() {
        return OutilReseau.getIp(); 
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
