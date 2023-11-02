package modele;

import modele.exceptions.MissingDataException;

/**
 * OutilCSV.java                                                                                 21/10/2015
 * No copyright.
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class OutilCSV {
    
    private final static int TAILLE_PAR_DEFAUT = 100;
    
    /** Prend en argument un tableau de données et le converti en une String au format CSV;
     * @param donnees
     * @return String donneesCSV
     * @throws MissingDataException 
     */
    public static String formaterToCSV(String[][] donnees) throws MissingDataException {
        if (donnees == null) {
            throw new MissingDataException();
        }
        String donneesCSV = "";
        for (String[] ligne : donnees) {
            int i = 0;
            for (String element : ligne) {
                donneesCSV += element + ";";
                if (i == ligne.length - 1) {
                    donneesCSV += "\n;";
                }
                i++;
            }
        }
        return donneesCSV;
    }

    /** Prend en argument une String au format CSV et la converti en un tableau de données.
     * @param donneesCSV
     * @return String[][] donnees
     * @throws MissingDataException 
     */
    public static String[][] formaterToDonnees(String donneesCSV) throws MissingDataException {
        if (donneesCSV == null || donneesCSV.equals("")) {
            throw new MissingDataException();
        }
        String[][] donnees = new String[TAILLE_PAR_DEFAUT][TAILLE_PAR_DEFAUT];
        String[] lignes = donneesCSV.split("\n;");
        int i = 0;
        for (String ligne : lignes) {
            String[] elements = ligne.split(";");
            for (int j = 0; j < elements.length; j++) {
                donnees[i][j] = elements[j];
            }
            i++;
        }
        return donnees;
    }
}
