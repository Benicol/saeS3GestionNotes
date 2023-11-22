/*
 * Modele.java                                                            9 nov 2023
 * IUT de Rodez, pas de droit d'auteur 
 */
package modele;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Modele.java                                                                                 21/10/2015
 * No copyright. 
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
     * Méthode qui renvoie un tableau bidimensionnel, contenant les données du 
     * paramétrage de l'applications, dans un format permettant la conversion en CSV
     * Afin de l'exporter vers un autre ordinateur
     * @param programme si le programme nationnal doit être exporté
     * @param modalite si les modalités d'évaluations doivent être exporté
     * @return donneesFinal
     * @throws IllegalArgumentException Si le paramétrage n'est pas déjà initialisé
     */
    public static String[][] exporter(boolean programme, boolean modalite) {
    	/*initialisation de la liste qui contiendras toute les données*/
    	ArrayList<ArrayList<String>> donnees = new ArrayList<>();
    	/*initialisation de la liste qui permetras d'insérer les données dans la précédente*/
    	ArrayList<String> donneesTmp = new ArrayList<>();
    	
    	/*Ajout d'un entête au données*/
    	donneesTmp.add("donnés exporter");
    	donnees.add((ArrayList<String>) donneesTmp.clone());
    	donneesTmp.clear();
    	
    	/*vérifie que le paramétrage est déja initialisé*/
    	if (!isParametrageInitialise()) {
    	    throw new IllegalArgumentException("L'application ne contient pas de données");
        }
    	
    	/*ajoute les infos du semestre au données*/
    	donneesTmp.add("Semestre");
    	donneesTmp.add(getParametrage().getSemestre());
    	donnees.add((ArrayList<String>) donneesTmp.clone());
    	donneesTmp.clear();
    	
    	/*ajoute les infos du parcours au données*/
    	donneesTmp.add("Parcours");
    	donneesTmp.add(getParametrage().getParcours());
    	donnees.add((ArrayList<String>) donneesTmp.clone());
    	donneesTmp.clear();
    	
    	/*ajout d'une ligne vide*/
    	donneesTmp.add("");
    	donnees.add((ArrayList<String>) donneesTmp.clone());
    	donneesTmp.clear();
    	
    	/*boucle pour insérer les Compétence*/
    	if (programme) {
        	for(Competence competence : getCompetences().values()) {
        	    donneesTmp.add("Compétence");
        	    donneesTmp.add(competence.getIdentifiant());
        	    donneesTmp.add(competence.getLibelle());
        	    donnees.add((ArrayList<String>) donneesTmp.clone());
        	    donneesTmp.clear();
        		
        	    donneesTmp.add("Type évaluations");
        	    donneesTmp.add("Identifians");
        	    donneesTmp.add("Libelle");
        	    donneesTmp.add("Poids");
        	    donnees.add((ArrayList<String>) donneesTmp.clone());
        	    donneesTmp.clear();
        		
        	    /*Boucle pour insérer les ressource de la compétence*/
        	    for(Ressource ressource : getRessources().values()) {
        	        /*vérifie que la ressource est bien dans la compétence*/
            		if(competence.getListeRessources().containsKey(ressource)){
            		    /*Insère les informations de la ressource*/
            		    donneesTmp.add("Ressource");
            		    donneesTmp.add(ressource.getIdentifiant());
            	    	    donneesTmp.add(ressource.getLibelle());
            	    	    /*Mets le poids au bon format*/
            	    	    donneesTmp.add(String.format("%.0f",competence.getListeRessources().get(ressource)*100));	
            	    	    donnees.add((ArrayList<String>) donneesTmp.clone());
            	    	    donneesTmp.clear();
            		}
        	    }
        		
        	    /*Boucle pour insérer les Sae de la compétence*/
        	    for(Sae sae : getSae().values()) {
        	        /*vérifie que la Sae est bien dans la compétence*/
            		if(competence.getListeSaes().containsKey(sae)){
            		    /*Si le nom de la sae est Portfolio, alors le type d'évaluations insérer est Portfolio*/
            		    if(sae.getLibelle().equals("Portfolio")) {
            			donneesTmp.add("Portfolio");
            		    }else {
            			donneesTmp.add("SAE");
            		    }	
            		    /*Insère les informations de la Sae*/
            		    donneesTmp.add(sae.getIdentifiant());
            	    	    donneesTmp.add(sae.getLibelle());
            	    	    /*Mets le poids au bon format*/
            	    	    donneesTmp.add(String.format("%.0f",competence.getListeSaes().get(sae)*100));	
            	    	    donnees.add((ArrayList<String>) donneesTmp.clone());
            	    	    donneesTmp.clear();
            		}
        	    }
        	    /*ajout d'une ligne vide*/
        	    donneesTmp.add("");
        	    donnees.add((ArrayList<String>) donneesTmp.clone());
        	    donneesTmp.clear();
        	}
    	}
    	if (modalite) {
        	for(Ressource ressource : getRessources().values()) {
        	    /*vérifie si la ressource posséde des évaluations*/
        	    if(!ressource.getListeEvaluations().isEmpty()) {
        	        /*Insère le nom et l'identifiant de la ressource*/
        	        donneesTmp.add("Ressource");
        	        donneesTmp.add(ressource.getIdentifiant());
        	        donneesTmp.add(ressource.getLibelle());
        	        donnees.add((ArrayList<String>) donneesTmp.clone());
        	        donneesTmp.clear();
        	        /*Insère le nom des colonne des données de l'évaluations*/
        	        donneesTmp.add("Type évaluations");
        	        donneesTmp.add("Date");
        	        donneesTmp.add("Poids");
        	        donnees.add((ArrayList<String>) donneesTmp.clone());
        	        donneesTmp.clear();
        	        for(Evaluation evaluation : ressource.getListeEvaluations()) {
        	            /*Insère le nom la date et le poids de l'évaluations*/
        	            donneesTmp.add(evaluation.getNom());
        	            donneesTmp.add(evaluation.getDate());
        	            /*Mets le poids au bon format*/
        	            donneesTmp.add(String.format("%.0f",evaluation.getPoids()*100));
        	            donnees.add((ArrayList<String>) donneesTmp.clone());
        	            donneesTmp.clear();
        	        }
        	        /*ajout d'une ligne vide*/
        	        donneesTmp.add("");
        	        donnees.add((ArrayList<String>) donneesTmp.clone());
        	        donneesTmp.clear();
        	    }
        	}
    	}
    	/*initialisation du tableau bidimensionnel qui contiendras les données à renvoyer*/
    	String[][] donneesFinal = new String[donnees.size()][0];
    	
    	/* On insère l'arrayList contenant les donnés du paramétrage
    	 * dans le tableau bidimensionnel renvoyer */
    	for (int i = 0; i < donneesFinal.length; i++) {
    	    donneesFinal[i] = donnees.get(i).toArray(new String[0]);
    	}
    	return donneesFinal;
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
        String messageErreur = "";
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
        try {
            boolean donneesValide = true;
            /* Vérifie si le format des données du CSV est valide */
            if (donnees == null || !(donneesValide = donnees[1].length >= 2 
                                                 && donnees[1][0].equals("Semestre") 
                                                 && isNumeric(donnees[1][1])
                                                 && donnees[2].length >= 2 
                                                 && donnees[2][0].equals("Parcours") 
                                                 && donnees[2][1].length() != 0)) {
                donneesValide = false;
                messageErreur = "des données sont éroné ligne 1 et 2 (semestre et parcours)";
            }
            /* Déclaration des variables pour stocker les informations du CSV */
            String semestre = "";
            String parcours = "";
            HashMap<String, String> listeSae = new HashMap<>();
            HashMap<String, String> listeRessource = new HashMap<>();
            ArrayList<String[][]> listeCompetence = new ArrayList<>();
            HashMap<String,ArrayList<Evaluation>> ressource = new HashMap<>();
            if (donneesValide) {
                /* Récupère le semestre et le parcours depuis les 1e lignes du CSV */
                semestre = donnees[1][1];
                parcours = donnees[2][1];
                /* Parcours des lignes du CSV à partir de la troisième ligne */
                for (int i = 3; i < donnees.length && donneesValide; i++) {
                    /* Si la ligne concerne une compétence */
                    if (donnees[i].length >= 1 && donnees[i][0].equals("Compétence")) {
                        messageErreur = "erreur lors de l'importation de la compétence ligne : " + i; 
                        ArrayList<String[]> competence = new ArrayList<>();;
                        String[] infoLigne = new String[2];
                        infoLigne[0] = donnees[i][1];
                        infoLigne[1] = donnees[i][2];
                        competence.add(infoLigne.clone());
                        int poids = 0;
                        i++;
                        
                        /* Parcours des lignes suivantes pour récupérer les informations
                         * (ressources et saé) liés à la compétence
                         */
                        while (poids != 100 && donneesValide) {
                            messageErreur = "Erreur lors de l'importation des "
                                    + "informations de la compétence ligne : " + i;
                            if (donnees[i][2].length() == 0) {
                                donneesValide = false;
                            }
                            i++;
                            int iComp = i;
                            infoLigne[0] = donnees[i][1];
                            infoLigne[1] = donnees[i][3];
                            /* Vérifie si la ligne concerne une ressource ou une SAE */
                            if (donnees[i][0].equals("Ressource")) {
                                if (!listeRessource.containsKey(infoLigne[0])) {
                                    messageErreur = "Erreur lors de l'importation "
                                            + "des informations de la ressource "
                                            + "ligne : " + i;
                                    listeRessource.put(infoLigne[0], donnees[i][2]);
                                }
                            } else if ((donnees[i][0].equals("Portfolio") || donnees[i][0].equals("SAE"))) {
                                if (!listeSae.containsKey(infoLigne[0])) {
                                    messageErreur = "Erreur lors de l'importation "
                                            + "des informations de la sae/du "
                                            + "portoflio ligne : " + i;
                                    listeSae.put(infoLigne[0], donnees[i][2]);
                                }
                            } else {
                                messageErreur = "Erreur lors de l'importation des "
                                        + "informations de la ressource/sae/du "
                                        + "portoflio ligne : " + i;
                                donneesValide = false;
                            }
                            messageErreur = "Erreur lors de l'importation des "
                                    + "informations de la compétence : " + iComp;
                            competence.add(infoLigne.clone());
                            poids += Integer.parseInt(donnees[i][3]);
                            if (poids > 100) {
                                messageErreur = "Le poids de la ressource ligne : " 
                                   + iComp + " est pas correcte ( " + poids + " ).";
                                donneesValide = false;
                            }
                        }
                        i++;
                        /* Ajoute la compétence à la liste des compétences */
                        listeCompetence.add(competence.toArray(new String[0][0]));
                    } else if (donnees[i].length >= 1 // Si la ligne concerne une ressource
                            && donnees[i][0].equals("Ressource")) { 
                        int iRess = i;
                        messageErreur = "erreur lors de l'importation de la "
                                + "ressource ligne : " + i; 
                        ArrayList<Evaluation> listeEvaluation = new ArrayList<>();
                        String[] infoEvaluation = new String[3];
                        String key = donnees[i][1];
                        int poids = 0;
                        i++;
                        /* Parcours des lignes suivantes pour récupérer les évaluations 
                         * de la ressource */
                        while (poids != 100 && donneesValide) {
                            i++;
                            messageErreur = "erreur lors de l'importation de la "
                                    + "ressource ligne : " + i; 
                            /* Vérifie si la composition de la ligne est bien :
                             * 1. Type d'évaluation, 2. poids
                             */
                            donneesValide = donnees[i][0].length() != 0;
                            donneesValide = donneesValide && isNumeric(donnees[i][2]);
                            
                            // Si la ligne est valide, ajoute le poids de l'évaluation au poids total
                            if (donneesValide) {
                                poids += Integer.parseInt(donnees[i][2]);
                                infoEvaluation[0] = donnees[i][0];
                                infoEvaluation[1] = donnees[i][1];
                                infoEvaluation[2] = donnees[i][2];
                                listeEvaluation.add(new Evaluation(infoEvaluation[0],
                                    Double.parseDouble(infoEvaluation[2]) / 100, 
                                    infoEvaluation[1]));
                            }
                            
                        }
                        if (poids > 100) {
                            messageErreur = "Le poids de la ressource ligne : " 
                                  + iRess + " est pas correcte ( " + poids + " ).";
                            donneesValide = false;
                        }
                        i++;
                        /* Ajoute les évaluations de la ressource à la liste des ressources */
                        ressource.put(key, listeEvaluation);
                    }
                }
            }
            if (!donneesValide) {
                throw new IllegalArgumentException("Le fichier n'est pas valide : " 
                                                   + messageErreur);
            }
            if (donneesValide) {
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
                sauvegarder();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Le fichier n'est pas valide : " 
                                               + messageErreur);
        }
        
    }
    
    /**
     * Méthode permettant d'importer les informations depuis un fichier importé 
     * depuis un autre ordinateur.
     *
     * @param donnees Tableau bidimensionnel contenant les données importées.
     * Les données sont organisées de manière à ce que donnees[i][j] représente
     * la valeur à la ligne i et colonne j.
     * @throws IllegalArgumentException Si le paramétrage que l'utilisateur essaye
     * d'importer est incompatible avec les données déjà présentes dans 
     * l'application.
     */
    public static void importerReseau(String[][] donnees) {
        /* Vérifie si le paramétrage est déjà initialisé */
        if (!isParametrageInitialise()) {
            // Si le paramétrage n'est pas initialisé, appelle la méthode générique d'importation
            importer(donnees);
        } else {
            // Vérifie la validité des données réseau par rapport au paramétrage existant
            if (isDonneesValideReseau(donnees)) {
                for (int i = 3; i < donnees.length; i++) {
                    if (donnees[i].length >= 1 && donnees[i][0].equals("Ressource") &&
                        (donnees[i + 1][0].equals("Type évaluation"))) {
                        // Récupère la ressource à partir du paramétrage
                        Ressource r = parametrage.getListeRessources().get(donnees[i][1]);
                        i += 2;
                        // Si la ressource a des évaluations existantes
                        if (!r.getListeEvaluations().isEmpty()) {
                            boolean trouve = false;
                            // Parcours des évaluations dans les données importées
                            while (donnees.length != i && donnees[i].length >= 1 &&
                                   !(donnees[i].length == 0)) {
                                // Vérifie si l'évaluation existe déjà
                                for (Evaluation e : r.getListeEvaluations()) {
                                    trouve = e.getNom().equals(donnees[i][0]);
                                    // Si l'évaluation existe et n'a pas de date mais la date est présente dans les données
                                    if (trouve && (e.getDate().isBlank() && !donnees[i][1].isBlank())) {
                                        e.setDate(donnees[i][1]);
                                    }
                                }
                                i++;
                                // Si l'évaluation n'a pas été trouvée, l'ajoute à la ressource
                                if (!trouve) {
                                    r.ajouterEvaluation(new Evaluation(donnees[i][0],
                                            Double.parseDouble(donnees[i][2]) / 100,
                                            donnees[i][1]));
                                }
                            }
                        } else {
                            // Si la ressource n'a pas d'évaluations existantes
                            ArrayList<Evaluation> listeEvaluation = new ArrayList<>();
                            // Parcours des évaluations dans les données importées
                            do {
                                listeEvaluation.add(new Evaluation(donnees[i][0],
                                        Double.parseDouble(donnees[i][2]) / 100,
                                        donnees[i][1]));
                                i++;
                            } while (donnees.length != i && donnees[i].length >= 1 &&
                                     !(donnees[i].length == 0));
                            // Ajoute les nouvelles évaluations à la ressource
                            for (Evaluation e : listeEvaluation) {
                                r.ajouterEvaluation(e);
                            }
                        }
                    }
                }
            } else {
                // Lance une exception si les données sont incompatibles avec le paramétrage
                throw new IllegalArgumentException("Les données sont incompatibles !");
            }
        }
    }   

    /* 
     * Méthode permettant d'importer les informations depuis 
     * un tableau bidimensionnel de données. Ce cas spécifique, qui n'arrive que 
     * lors d'une importation via un autre ordinateur, ne nécessite pas de vérifier
     * si les données sont valides (la vérification ayant déjà été faite lors de 
     * l'importation par l'autre ordinateur).
     * @param donnees tableau bidimensionnel contenant toutes les données relatives
     * à un semestre.
     */
    private static void importer(String[][] donnees) {
    
        /* Déclaration des variables pour stocker les informations du CSV */
        String semestre = donnees[1][1];
        String parcours = donnees[2][1];
        HashMap<String, String> listeSae = new HashMap<>();
        HashMap<String, String> listeRessource = new HashMap<>();
        ArrayList<String[][]> listeCompetence = new ArrayList<>();
        HashMap<String,ArrayList<Evaluation>> ressource = new HashMap<>();

        /* Parcours des lignes du CSV à partir de la troisième ligne */
        for (int i = 3; i < donnees.length; i++) {
            /* Si la ligne concerne une compétence */
            if (donnees[i].length >= 1 && donnees[i][0].equals("Compétence")) {
                ArrayList<String[]> competence = new ArrayList<>();;
                String[] infoLigne = new String[2];
                infoLigne[0] = donnees[i][1];
                infoLigne[1] = donnees[i][2];
                competence.add(infoLigne.clone());
                int poids = 0;
                i++;
                
                /* 
                 * Parcours des lignes suivantes pour récupérer les informations
                 * (ressources et saé) liés à la compétence
                 */
                while (poids != 100) {
                    i++;
                    int iComp = i;
                    infoLigne[0] = donnees[i][1];
                    infoLigne[1] = donnees[i][3];
                    /* Vérifie si la ligne concerne une ressource ou une SAE */
                    if (donnees[i][0].equals("Ressource")) {
                        listeRessource.put(infoLigne[0], donnees[i][2]);
                    } else {
                        listeSae.put(infoLigne[0], donnees[i][2]);
                    } 
                    competence.add(infoLigne.clone());
                    poids += Integer.parseInt(donnees[i][3]);
                }
                i++;
                /* Ajoute la compétence à la liste des compétences */
                listeCompetence.add(competence.toArray(new String[0][0]));
            } else if (donnees[i].length >= 1 // Si la ligne concerne une ressource
                    && donnees[i][0].equals("Ressource")) { 
                int iRess = i;
                ArrayList<Evaluation> listeEvaluation = new ArrayList<>();
                String[] infoEvaluation = new String[3];
                String key = donnees[i][1];
                int poids = 0;
                i++;
                /* Parcours des lignes suivantes pour récupérer les évaluations 
                 * de la ressource */
                while (poids != 100) {
                    i++;
                    poids += Integer.parseInt(donnees[i][2]);
                    infoEvaluation[0] = donnees[i][0];
                    infoEvaluation[1] = donnees[i][1];
                    infoEvaluation[2] = donnees[i][2];
                    listeEvaluation.add(new Evaluation(infoEvaluation[0],
                        Double.parseDouble(infoEvaluation[2]) / 100, 
                        infoEvaluation[1]));
                    /* Ajoute les évaluations de la ressource à la liste des ressources */
                    ressource.put(key, listeEvaluation);
                }
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
        sauvegarder();
    }
    
    /*
     * Vérifie la validité des données réseau par rapport aux paramètres de
     * configuration.
     *
     * @param donnees Un tableau bidimensionnel contenant les données réseau.
     * Les données sont organisées de manière à ce que donnees[i][j] représente la 
     * valeur à la ligne i et colonne j.
     * @return true si les données sont valides par rapport aux paramètres de
     * configuration, false sinon.
     */
    private static boolean isDonneesValideReseau(String[][] donnees) {
        // Vérifie la validité du semestre et du parcours dans les données
        if (!donnees[1][1].equals(parametrage.getSemestre()) ||
            !donnees[2][1].equals(parametrage.getParcours())) {
            return false;
        }

        // Variable indiquant si les données sont valides
        boolean donneesValides = true;

        // Parcours des lignes du tableau à partir de l'indice 4
        for (int i = 4; i < donnees.length && donneesValides; i++) {
            // Si la ligne concerne une compétence
            if (donnees[i].length >= 1 && donnees[i][0].equals("Compétence")) {
                // Vérifie la validité de la compétence
                donneesValides = parametrage.getListeCompetences().containsKey(donnees[i][1])
                    && parametrage.getListeCompetences().get(donnees[i][1])
                        .getLibelle().equals(donnees[i][2]);
                int poids = 0;

                Competence competence = parametrage.getListeCompetences().get(donnees[i][1]);
                i++;

                /* 
                 * Parcours des lignes suivantes pour récupérer les informations
                 * liées à la compétence
                 */

                while (poids != 100 && donneesValides) {
                    i++;
                    // Vérifie si la ligne concerne une ressource ou une SAE
                    if (donnees[i][0].equals("Ressource")) {
                        donneesValides = (competence.getListeRessources().containsKey(
                                parametrage.getListeRessources().get(donnees[i][1]))
                                && parametrage.getListeRessources().get(donnees[i][1])
                                    .getLibelle().equals(donnees[i][2])
                                && competence.getListeRessources()
                                    .get(parametrage.getListeRessources().get(donnees[i][1]))
                                    .equals(Double.parseDouble(donnees[i][3]) / 100))
                                || competence.getListeRessources().isEmpty();
                    } else if (donnees[i][0].equals("Portfolio") ||
                               donnees[i][0].equals("SAE")) {
                        donneesValides = competence.getListeSaes().containsKey(
                                parametrage.getListeSaes().get(donnees[i][1]));
                    }
                    poids += Integer.parseInt(donnees[i][3]);
                }
                i++;
            } else if (donnees[i][0].equals("Ressource")) { // Si la ligne concerne 
                                                            // une ressource
                Ressource r = parametrage.getListeRessources().get(donnees[i][1]);
                donneesValides = r.getIdentifiant().equals(donnees[i][1]) &&
                                 r.getLibelle().equals(donnees[i][2]);
                i += 2;
                boolean donneesValidesTmp = true;

                // Vérifie la validité des évaluations de la ressource
                if (!r.getListeEvaluations().isEmpty()) {
                    do {
                        donneesValidesTmp = false;
                        for (Evaluation e : r.getListeEvaluations()) {
                            if (e.getNom().equals(donnees[i][0]) &&
                                e.getPoids().equals(Double.parseDouble(donnees[i][2]) / 100)
                                && (e.getDate().equals(donnees[i][1]) || e.getDate().isBlank())) {
                                donneesValidesTmp = true;
                            }
                        }
                        i++;
                    } while (donnees.length != i && (donnees[i].length != 0) &&
                             donneesValidesTmp);
                }
                donneesValides = donneesValidesTmp;
            }
        }

        return donneesValides;
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
      * Setter du parametrage (A UTILISER UNIQUEMENT POUR LES TESTS)
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
