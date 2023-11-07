/*
 * TestParametrage.java                                                   3 nov 2023                                                                                21/10/2015
 * IUT de Rodez, pas de copyright
 */

package modele.test;

import modele.Parametrage;
import modele.Ressource;
import modele.Sae;
import modele.Competence;
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
        String[][][] donneesCompetences = {{
                                            {"C2.1","Compétence de test 1"},
                                            {"R2.01", "60"}, 
                                            {"S2.01", "38"}, 
                                            {"P2.01", "2"}
                                            },
                                           {
                                            {"C2.2","Compétence de test 2"},
                                            {"R2.01", "30"},
                                            {"R2.02", "30"},
                                            {"S2.02", "38"}, 
                                            {"P2.01", "2"}
                                           }};
        HashMap<String, String> donneesSaes = new HashMap<>();
        donneesSaes.put("S2.01", "sae de test 1");
        donneesSaes.put("S2.02", "sae de test 2");
        donneesSaes.put("P2.01", "Portfolio");
        HashMap<String, String> donneesRessources = new HashMap<>();
        donneesRessources.put("R2.01", "ressource de test 1");
        donneesRessources.put("R2.02", "ressource de test 2");
        
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
        HashMap<String, Ressource> ressources = parametrage.getListeRessources();
        assertNotNull(ressources);
        assertFalse(ressources.isEmpty());
        assertEquals("R2.01", ressources.get("R2.01").getIdentifiant());
        assertEquals("ressource de test 2", ressources.get("R2.02").getLibelle());
    }

    /**
     * Teste la méthode getListeCompetences.
     */
    @Test
    public void testGetListeCompetences() {
        HashMap<String, Competence> competences = parametrage.getListeCompetences();
        assertNotNull(competences);
        assertFalse(competences.isEmpty());
        assertEquals("C2.1", competences.get("C2.1").getIdentifiant());
        assertTrue(competences.get("C2.1").getListeRessources()
                .containsKey(parametrage.getListeRessources().get("R2.01")));
    }

    /**
     * Teste la méthode getListeSaes.
     */
    @Test
    public void testGetListeSaes() {
        HashMap<String, Sae> saes = parametrage.getListeSaes();
        assertNotNull(saes);
        assertFalse(saes.isEmpty());
        assertEquals("S2.01", saes.get("S2.01").getIdentifiant());
        assertEquals("sa de test 2", saes.get("S2.02").getLibelle());
        assertEquals("Portfolio", saes.get("P2.01").getLibelle());
    }
}
