/*
 * TestRessource.java                                  7 nov. 2023
 * IUT Rodez, info1 2022-2023, ni copyright ni "copyleft"
 */
package modele.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modele.Evaluation;
import modele.Ressource;

/** Classe permettant de tester la classe modele.Ressource()
 * @author ugosc
 *
 */
class TestRessource {
    
    private static Evaluation eval1; 
    private static Evaluation eval2; 
    private static Evaluation eval3; 
    
    private static ArrayList<Evaluation> liste1;
    private static ArrayList<Evaluation> liste2;
    
    private static String intitule1;
    private static String intitule2;
    
    private static Ressource ressource1;
    private static Ressource ressource2;
    private static Ressource ressource3;
    private static Ressource ressource4;
    private static Ressource ressource5;
    
    @BeforeEach
    public void initialisation() {
        ressource1 = new Ressource("R1.01", "développement web");
        ressource2 = new Ressource("R1.02", "base de données ");
        ressource3 = new Ressource("R1.03", "proba");
        ressource4 = new Ressource("R1.04", "PPP");
        new Ressource("R1.04", "com");
        ressource5 = new Ressource("R1.06", "crypto");
        
        eval1 = new Evaluation("QCM",0.01,"05/04/23");
        eval2 = new Evaluation("controle",0.4,"08/07/23");
        eval3 = new Evaluation("controle",0.25,"05/04/23");
        
        liste1 = new ArrayList<>();
        liste2 = new ArrayList<>();
        
        intitule1 = "R1.01 : développement web";
        intitule2 = "développement  .01";
        
        liste1.add(eval1);
        liste1.add(eval2);
        
        liste2.add(eval1);
        liste2.add(eval2);
        liste2.add(eval3);
        
        ressource1.ajouterEvaluation(new Evaluation("controle",0.5,"05/04/23"));
        ressource2.ajouterEvaluation(eval1);
        ressource2.ajouterEvaluation(eval2);
        ressource3.ajouterEvaluation(new Evaluation("controle",0.1,"02/04/23"));
        ressource4.ajouterEvaluation(new Evaluation("controle",0.7,"15/06/23"));
        
        ressource5.ajouterEvaluation(new Evaluation("controle",1.0,"02/04/23"));
        
    }
    
    @Test
    public void testRessource() {
        assertDoesNotThrow(() -> new Ressource("R1.05","réseaux"));
    }
    
    @Test
    public void testAjouterEvaluation() {
        Assertions.assertTrue(ressource1.ajouterEvaluation(new Evaluation("controle",0.5,"05/04/23")));
        Assertions.assertTrue(ressource2.ajouterEvaluation(new Evaluation("controle",0.5,"05/05/23")));
        Assertions.assertFalse(ressource5.ajouterEvaluation(new Evaluation("controle",0.3,"05/08/23")));
        Assertions.assertFalse(ressource4.ajouterEvaluation(new Evaluation("controle",0.35,"01/08/23")));
    }
    
    @Test
    public void testModifierEvaluation() {
        Assertions.assertTrue(ressource1.modifierEvaluation(0,1,"qcm"));
        Assertions.assertTrue(ressource2.modifierEvaluation(0,2,"03/11/23"));
        Assertions.assertFalse(ressource3.modifierEvaluation(3,3,"12.4"));
        Assertions.assertFalse(ressource3.modifierEvaluation(-1,2,"23/12/23"));
        Assertions.assertTrue(ressource4.modifierEvaluation(0,3,"14.5"));
        Assertions.assertTrue(ressource3.modifierEvaluation(0,4,"0.35"));
    }
    
    @Test
    public void testSupprimerEvaluation() {
        Assertions.assertTrue(ressource1.supprimerEvaluation(0));
        Assertions.assertTrue(ressource2.supprimerEvaluation(0));
        Assertions.assertFalse(ressource1.supprimerEvaluation(4));
        Assertions.assertFalse(ressource2.supprimerEvaluation(-1));
        Assertions.assertFalse(ressource3.supprimerEvaluation(6));
        
    }
    
    @Test
    public void testGetLibelle() {
        Assertions.assertEquals("développement web", ressource1.getLibelle());
        Assertions.assertEquals("base de données ", ressource2.getLibelle());
        Assertions.assertNotEquals("base de données", ressource3.getLibelle());
        Assertions.assertNotEquals("développement web", ressource4.getLibelle());
        
    }
    
    @Test
    public void testGetIdentifiant() {
        Assertions.assertEquals("R1.01", ressource1.getIdentifiant());
        Assertions.assertEquals("R1.02", ressource2.getIdentifiant());
        Assertions.assertNotEquals("R1.06", ressource3.getIdentifiant());
        Assertions.assertNotEquals("R2.04", ressource4.getIdentifiant());
        
    }
    
    @Test
    public void testGetListeEvaluation() {
        Assertions.assertEquals(liste1, ressource2.getListeEvaluations());
        Assertions.assertNotEquals(liste2, ressource2.getListeEvaluations());
        
    }
    
    @Test
    public void testCreerIntitule() {
        Assertions.assertEquals(intitule1, ressource1.creerIntitule());
        Assertions.assertNotEquals(intitule2, ressource1.creerIntitule());
        
    }
    
    @Test
    public void testCalculerMoyenne() {
        ressource5.modifierEvaluation(0, 3, "12.5");
        Assertions.assertEquals(12.5, ressource5.calculerMoyenne());
        Assertions.assertEquals(0.0, ressource1.calculerMoyenne());
        Assertions.assertNotEquals(5.0, ressource2.calculerMoyenne());
        Assertions.assertNotEquals(0.5, ressource4.calculerMoyenne());
        
    }
    
    
    
    
    

}
