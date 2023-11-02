/*
 * TestOutilCSV.java                                  2 nov. 2023
 * IUT Rodez, info1 2022-2023, ni copyright ni "copyleft"
 */
package modele.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modele.OutilCSV;
import modele.exceptions.MissingDataException;

/** TODO comment class responsibility (SRP)
 * @author Ugo Schardt
 *
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
        
        donneesCSVCorrectes = "test1;test2;test3;\n;test4;test5;test6;\n;test7;test8;test9;\n;";
        donneesCSVIncorrectes1 = null;
        donneesCSVIncorrectes2 = "";
    }
    
    @Test
    public void testFormaterToCSV() {
        assertDoesNotThrow(() -> OutilCSV.formaterToCSV(donneesCorrectes));
        try {
            assertEquals(OutilCSV.formaterToCSV(donneesCorrectes), donneesCSVCorrectes);
        } catch (MissingDataException e) {
            
        }
        
        assertThrows(MissingDataException.class, () -> OutilCSV.formaterToCSV(donneesIncorrectes));
    }
    
    @Test
    public void testFormaterToDonnees() {
        assertDoesNotThrow(() -> OutilCSV.formaterToDonnees(donneesCSVCorrectes));
        try {
            OutilCSV.formaterToDonnees(donneesCSVCorrectes).equals(donneesCorrectes);
        } catch (MissingDataException e1) {

        }
        
        assertThrows(MissingDataException.class, () -> OutilCSV.formaterToDonnees(donneesCSVIncorrectes1));
        assertThrows(MissingDataException.class, () -> OutilCSV.formaterToDonnees(donneesCSVIncorrectes2));
    }
}
