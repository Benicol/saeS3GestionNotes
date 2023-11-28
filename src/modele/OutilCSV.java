/*
 * OutilCSV.java                                                         2 nov. 2023
 * IUT Rodez, info1 2022-2023, ni copyright ni "copyleft"
 */
package modele;


/**
 * Classe outil permettant de :
 * - Convertir un tableau de données en une String au format CSV
 * - Convertir une string au format CSV en un tableau de données
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class OutilCSV {
    
    /** 
     * Prend en argument un tableau de données et le converti en une String au 
     * format CSV;
     * @param donnees fournis pour être transformer au format CSV
     * @return String contenant les donnees dans le tableau donnees formater en csv
     * @throws IllegalArgumentException si les donnees son null 
     */
    public static String formaterToCSV(String[][] donnees) 
            throws IllegalArgumentException {
        if (donnees == null) {
            throw new IllegalArgumentException("donnees vides");
        }
        StringBuilder donneesCSV = new StringBuilder();
        for (String[] ligne : donnees) {
            for (int i = 0; i < ligne.length; i++) {
                donneesCSV.append(ligne[i] + ";");
                if (i == ligne.length - 1) {
                    donneesCSV.deleteCharAt(donneesCSV.length() - 1);
                    donneesCSV.append("\n");
                }
            }
        }
        return donneesCSV.toString().substring(0, donneesCSV.length() - 1);
    }

    /** 
     * Prend en argument une String au format CSV et la converti en un tableau 
     * de données.
     * @param donneesCSV donnees formater en csv
     * @return String[][] donnees formater en tableau de tableau
     * @throws IllegalArgumentException si les donnees son null
     */
    public static String[][] formaterToDonnees(String donneesCSV) 
            throws IllegalArgumentException {
        if (donneesCSV == null || donneesCSV.equals("")) {
            throw new IllegalArgumentException("donnees vides");
        }
        String[] lignes = donneesCSV.split("\n");
        String[][] donnees = new String[lignes.length][0];
        for (int i = 0; i < lignes.length; i++) {
            if (lignes[i].isBlank()) {
                donnees[i] = new String[0];
            } else {
                donnees[i] = lignes[i].split(";");
            }
        }
        return donnees;
    }
}
