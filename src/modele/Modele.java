package modele;

import java.util.HashMap;

/**
 * Modele.java                                                                                 21/10/2015
 * No copyright. 
 */
public class Modele {
	
	
    private static Parametrage parametrage;
    private static Utilisateur utilisateur;
    
    /**
     * Méthode qui mets paramétrage à null 
     */
    public static void reset() {
        parametrage = null;
    } 
    public void importer() {
        //TODO
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
    	if(utilisateur == null) {
    		utilisateur = new Utilisateur();
    	}
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
      * méthode temporaire
      */
    public static void setParametrage(Parametrage parametre) {
    	parametrage = parametre;
    }
}
