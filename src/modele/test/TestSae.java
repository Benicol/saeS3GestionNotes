/*
 * TestSae.java                                  2 nov. 2023
 * IUT Rodez, info1 2022-2023, ni copyright ni "copyleft"
 */
package modele.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modele.Sae;

/** Classe de test de la classe Sae
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
class TestSae {
    
    private static Sae saeTest;
    private static double noteCorrecte;
    private static double noteIncorrecte1;
    private static double noteIncorrecte2;
    private static String libelleTest;
    private static String identifiantTest;
    private static String intituleTest;

    @BeforeAll
    public static void initialisation() {
        noteCorrecte = 10.0;
        noteIncorrecte1 = -5;
        noteIncorrecte2 = 25;
        libelleTest = "libelleTest";
        identifiantTest = "identifiantTest";
        intituleTest = "identifiantTest : libelleTest";
        saeTest = new Sae(identifiantTest, libelleTest);
    }
    
    @Test
    public void testConstructeurSae() {
        assertThrows(IllegalArgumentException.class, () -> new Sae("", ""));
        assertThrows(IllegalArgumentException.class, () -> new Sae(null, null));
        assertThrows(IllegalArgumentException.class, () -> new Sae("test", ""));
        assertThrows(IllegalArgumentException.class, () -> new Sae("", "test"));
        assertThrows(IllegalArgumentException.class, () -> new Sae("test", null));
        assertThrows(IllegalArgumentException.class, () -> new Sae(null, "test"));
        assertDoesNotThrow(() -> new Sae("test", "test"));
    }
    
    @Test
    public void testGetNote() {
        saeTest.setNote(noteCorrecte);
        assertEquals(noteCorrecte, saeTest.getNote());
    }
    
    @Test
    public void testSetNote() {
        assertThrows(IllegalArgumentException.class, 
                () -> saeTest.setNote(noteIncorrecte1));
        assertThrows(IllegalArgumentException.class, 
                () -> saeTest.setNote(noteIncorrecte2));
        assertDoesNotThrow(() -> saeTest.setNote(noteCorrecte));
    }
    
    @Test
    public void testGetLibelle() {
        assertEquals(libelleTest, saeTest.getLibelle());
    }
    
    @Test
    public void testGetIdentifiant() {
        assertEquals(identifiantTest, saeTest.getIdentifiant());
    }
    
    @Test
    public void testCreerIntitule() {
        assertEquals(intituleTest, saeTest.creerIntitule());
    }

}
