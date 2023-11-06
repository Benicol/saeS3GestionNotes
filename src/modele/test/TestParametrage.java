/*
 * TestParametrage.java                                                   3 nov 2023                                                                                21/10/2015
 * IUT de Rodez, pas de copyright
 */

package modele.test;

import modele.Parametrage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;

/**
 * Classe de test JUnit pour la classe Parametrage.
 * 
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class TestParametrage {

    private Parametrage parametrage;

    /**
     * Initialise un objet Parametrage avec des valeurs valides avant chaque test.
     */
    @Before
    public void setUp() {
        // Initialise un objet Parametrage avec des valeurs valides
        String semestre = "2";
        String parcours = "A";
        String[][][] donneesCompetences = new String[2][2][2];
        HashMap<String, String> donneesSaes = new HashMap<>();
        HashMap<String, String> donneesRessources = new HashMap<>();
        
        parametrage = new Parametrage(semestre, parcours, donneesCompetences, donneesSaes, donneesRessources);
    }
    
    /**
     * Teste le constructeur de Parametrage avec des paramètres nuls.
     */
    @Test
    public void testConstructorWithNullParameters() {
        // Test avec semestre nul
        try {
            new Parametrage(null, "A", null, null, null);
            fail("IllegalArgumentException attendue pour le semestre nul");
        } catch (IllegalArgumentException e) {
            assertEquals("Aucun paramètre ne peut être nul.", e.getMessage());
        }

        // Test avec parcours nul
        try {
            new Parametrage("2", null, null, null, null);
            fail("IllegalArgumentException attendue pour le parcours nul");
        } catch (IllegalArgumentException e) {
            assertEquals("Aucun paramètre ne peut être nul.", e.getMessage());
        }

        // Test avec compétences nulles
        try {
            new Parametrage("3", "D", null, null, null);
            fail("IllegalArgumentException attendue pour les compétences nulles");
        } catch (IllegalArgumentException e) {
            assertEquals("Aucun paramètre ne peut être nul.", e.getMessage());
        }

        // Test avec SAE nulles
        try {
            new Parametrage("4", "XXX", new String[2][2][2], null, null);
            fail("IllegalArgumentException attendue pour les SAE nulles");
        } catch (IllegalArgumentException e) {
            assertEquals("Aucun paramètre ne peut être nul.", e.getMessage());
        }

        // Test avec ressources nulles
        try {
            new Parametrage("5", "A", new String[2][2][2], new HashMap<>(), null);
            fail("IllegalArgumentException attendue pour les ressources nulles");
        } catch (IllegalArgumentException e) {
            assertEquals("Aucun paramètre ne peut être nul.", e.getMessage());
        }
    }

    /**
     * Teste la méthode getSemestre.
     */
    @Test
    public void testGetSemestre() {
        assertEquals("2", parametrage.getSemestre());
    }

    /**
     * Teste la méthode getParcours.
     */
    @Test
    public void testGetParcours() {
        assertEquals("A", parametrage.getParcours());
    }

    /**
     * Teste la méthode getListeRessources.
     */
    @Test
    public void testGetListeRessources() {
        HashMap<String, String> ressources = parametrage.getListeRessources();
        assertNotNull(ressources);
        assertTrue(ressources.isEmpty());
    }

    /**
     * Teste la méthode getListeCompetences.
     */
    @Test
    public void testGetListeCompetences() {
        String[][][] competences = parametrage.getListeCompetences();
        assertNotNull(competences);
        assertEquals(2, competences.length);
    }

    /**
     * Teste la méthode getListeSaes.
     */
    @Test
    public void testGetListeSaes() {
        HashMap<String, String> saes = parametrage.getListeSaes();
        assertNotNull(saes);
        assertTrue(saes.isEmpty());
    }
}
