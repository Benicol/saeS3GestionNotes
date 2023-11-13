/*
 * TestOutilCSV.java                                  2 nov. 2023
 * IUT Rodez, info1 2022-2023, ni copyright ni "copyleft"
 */
package modele.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modele.OutilCSV;

/** Classe de test de la classe OutilCSV
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
class TestOutilCSV {
    
    private static String[][] donneesCorrectes;
    private static String[][] donneesIncorrectes;
    
    private static String donneesCSVCorrectes;
    private static String donneesCSVIncorrectes1;
    private static String donneesCSVIncorrectes2;
    
    @BeforeAll
    public static void initialisation() {
        donneesCorrectes = new String[3][3];
        int i = 1;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                donneesCorrectes[j][k] = "test" + i;
                i++;
            }
        }
        donneesIncorrectes = null;
        
        donneesCSVCorrectes = "test1;test2;test3\ntest4;test5;test6\ntest7;test8;test9";
        donneesCSVIncorrectes1 = null;
        donneesCSVIncorrectes2 = "";
    }
    
    @Test
    public void testFormaterToCSV() {
        assertDoesNotThrow(() -> OutilCSV.formaterToCSV(donneesCorrectes));
        try {
            assertEquals(donneesCSVCorrectes, OutilCSV.formaterToCSV(donneesCorrectes));
        } catch (IllegalArgumentException e) {
            System.out.println("erreur de donnees");
        }
        
        assertThrows(IllegalArgumentException.class, () -> OutilCSV.formaterToCSV(donneesIncorrectes));
    }
    
    @Test
    public void testFormaterToDonnees() {
        assertDoesNotThrow(() -> OutilCSV.formaterToDonnees(donneesCSVCorrectes));
        try {
            OutilCSV.formaterToDonnees(donneesCSVCorrectes).equals(donneesCorrectes);
        } catch (IllegalArgumentException e) {
            System.out.println("erreur de donnees");
        }
        
        assertThrows(IllegalArgumentException.class, () -> OutilCSV.formaterToDonnees(donneesCSVIncorrectes1));
        assertThrows(IllegalArgumentException.class, () -> OutilCSV.formaterToDonnees(donneesCSVIncorrectes2));
    }
}
