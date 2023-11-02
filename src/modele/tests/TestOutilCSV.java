/*
 * TestOutilCSV.java                                  2 nov. 2023
 * IUT Rodez, info1 2022-2023, ni copyright ni "copyleft"
 */
package modele.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import modele.OutilCSV;
import modele.exceptions.MissingDataException;

/** TODO comment class responsibility (SRP)
 * @author Ugo Schardt
 *
 */
class TestOutilCSV {
    
    private static String[][] donneesCorrectes = creerDonneesCorrectes();
    private static String[][] donneesIncorrectes = null;
    
    private static String donneesCSVCorrectes = "test1;test2;test3;\n;test4;test5;test6;\n;test7;test8;test9;\n;";
    private static String donneesCSVIncorrectes1 = null;
    private static String donneesCSVIncorrectes2 = "";
    
    private static String[][] creerDonneesCorrectes() {
        String[][] donneesCorrectes = new String[3][3];
        int i = 1;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                donneesCorrectes[j][k] = "test" + i;
                i++;
            }
        }
        return donneesCorrectes;
    }
    
    @Test
    static void testFormaterToCSV() {
        assertDoesNotThrow(() -> OutilCSV.formaterToCSV(donneesCorrectes));
        try {
            assertEquals(OutilCSV.formaterToCSV(donneesCorrectes), donneesCSVCorrectes);
        } catch (MissingDataException e) {
            
        }
        
        assertThrows(MissingDataException.class, () -> OutilCSV.formaterToCSV(donneesIncorrectes));
    }
    
    @Test
    static void testFormaterToDonnees() {
        assertDoesNotThrow(() -> OutilCSV.formaterToDonnees(donneesCSVCorrectes));
        try {
            OutilCSV.formaterToDonnees(donneesCSVCorrectes).equals(donneesCorrectes);
        } catch (MissingDataException e1) {

        }
        
        assertThrows(MissingDataException.class, () -> OutilCSV.formaterToDonnees(donneesCSVIncorrectes1));
        assertThrows(MissingDataException.class, () -> OutilCSV.formaterToDonnees(donneesCSVIncorrectes2));
    }
    
    @AfterAll
    static void main() {
        testFormaterToCSV();
        testFormaterToDonnees();
    }

}
