/*
 * TestCompetence.java                                  8 nov. 2023
 * IUT Rodez, info1 2022-2023, ni copyright ni "copyleft"
 */
package modele.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modele.Competence;
import modele.Ressource;
import modele.Sae;
import modele.Evaluation;

/** Classe de test de la classe Competence
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 *
 */
class TestCompetence {
    
    private static Competence competenceTest;
    private static String libelleTest;
    private static String identifiantTest;
    private static String intituleTest;
    private static HashMap<Ressource, Double> listeRessourcesTest;
    private static HashMap<Sae, Double> listeSaesTest;
    private static Evaluation[] tableauEvaluationTest;
    private static Ressource[] tableauRessourceTest;
    private static Sae[] tableauSaeTest;

    @BeforeAll
    public static void initialisation() {
        libelleTest = "libelleTest";
        identifiantTest = "identifiantTest";
        intituleTest = "identifiantTest : libelleTest";
        
        competenceTest = new Competence(identifiantTest, libelleTest);
        listeRessourcesTest = new HashMap<Ressource, Double>();
        listeSaesTest = new HashMap<Sae, Double>();
        
        tableauEvaluationTest[0] = new Evaluation("E0", 25, "01/01/2023");
        tableauEvaluationTest[0].setNote(2.5);
        tableauEvaluationTest[1] = new Evaluation("E1", 25, "01/02/2023");
        tableauEvaluationTest[1].setNote(5.0);
        tableauEvaluationTest[2] = new Evaluation("E2", 25, "01/03/2023");
        tableauEvaluationTest[2].setNote(10.0);
        tableauEvaluationTest[3] = new Evaluation("E3", 25, "01/04/2023");
        tableauEvaluationTest[3].setNote(20.0);
        
        tableauRessourceTest[0] = new Ressource(identifiantTest, libelleTest);
        tableauRessourceTest[1] = new Ressource(identifiantTest, libelleTest);
        tableauRessourceTest[2] = new Ressource(identifiantTest, libelleTest);
        
        for (Ressource ressourceTest : tableauRessourceTest) {
            for (Evaluation evaluationTest : tableauEvaluationTest) {
                ressourceTest.ajouterEvaluation(evaluationTest);
            }
        }
        
        tableauSaeTest[0] = new Sae(identifiantTest, libelleTest);
        tableauSaeTest[0].setNote(15.0);
        tableauSaeTest[1] = new Sae(identifiantTest, libelleTest);
        tableauSaeTest[1].setNote(17.25);
        
        for (Ressource ressourceTest : tableauRessourceTest) {
            listeRessourcesTest.put(ressourceTest, 0.2);
        }
        
        for (Sae saeTest : tableauSaeTest) {
            listeSaesTest.put(saeTest, 0.2);
        }
    }

    @Test
    public void testConstructeurCompetence() {
        assertThrows(IllegalArgumentException.class, () -> new Competence("", ""));
        assertThrows(IllegalArgumentException.class, () -> new Competence(null, null));
        assertThrows(IllegalArgumentException.class, () -> new Competence("test", ""));
        assertThrows(IllegalArgumentException.class, () -> new Competence("", "test"));
        assertThrows(IllegalArgumentException.class, () -> new Competence("test", null));
        assertThrows(IllegalArgumentException.class, () -> new Competence(null, "test"));
        assertDoesNotThrow(() -> new Competence("test", "test"));
    }
    
    @Test
    public void testGetLibelle() {
        assertEquals(libelleTest, competenceTest.getLibelle());
    }
    
    @Test
    public void testGetIdentifiant() {
        assertEquals(identifiantTest, competenceTest.getIdentifiant());
    }
    
    @Test
    public void testCreerIntitule() {
        assertEquals(intituleTest, competenceTest.creerIntitule());
    }
    
    @Test
    public void testGetListeRessources() {
        competenceTest.setListeRessources(listeRessourcesTest);
        assertEquals(listeRessourcesTest, competenceTest.getListeRessources());
    }
    
    @Test
    public void testGetListeSaes() {
        competenceTest.setListeSaes(listeSaesTest);
        assertEquals(listeSaesTest, competenceTest.getListeSaes());
    }
    
    @Test
    public void testSetListeRessources() {
        competenceTest.setListeRessources(listeRessourcesTest);
        assertEquals(listeRessourcesTest, competenceTest.getListeRessources());
    }
    
    @Test
    public void testSetListeSaes() {
        competenceTest.setListeSaes(listeSaesTest);
        assertEquals(listeSaesTest, competenceTest.getListeSaes());
    }
    
    @Test
    public void testCalculerMoyenne() {
        fail("Not yet implemented");
    }
    
    @Test
    public void testIsCalculable() {
        assertTrue(competenceTest.isCalculable());
        
        //TODO changer un truc pour provoquer erreur
    }

}
