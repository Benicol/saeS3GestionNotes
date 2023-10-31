/*
 * TestEvaluation.java                                      31 Oct 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package modele.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modele.Evaluation;

/**
 * Tests en JUnit de la classe "Evaluation".
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
class TestEvaluation {
    
    /* Déclaration d'une Evaluation pour tester la classe */
    private Evaluation evaluation;

    /**
     * Création d'une évaluation avant l'execution de chaque test.
     */
    @BeforeEach
    public void setUp() {
        // Crée une instance d'Evaluation avant chaque test
        evaluation = new Evaluation("Test d'évaluation", 10, "10/05/2023");
    }

    /**
     * Test du constructeur de la classe Evaluation. 
     */
    @Test
    public void testConstructeur() {
        assertEquals("Test d'évaluation", evaluation.getNom());
        assertEquals(10, evaluation.getPoids());
        assertEquals("10/05/2023", evaluation.getDate());
        assertNull(evaluation.getNote());
    }

    /**
     * Test des getteurs et des setteurs de la classe Evaluation. 
     */
    @Test
    public void testSettersAndGetters() {
        evaluation.setNom("Nouveau nom");
        evaluation.setPoids(5);
        evaluation.setDate("15/06/2023");
        evaluation.setNote(8.5);

        assertEquals("Nouveau nom", evaluation.getNom());
        assertEquals(5, evaluation.getPoids());
        assertEquals("15/06/2023", evaluation.getDate());
        assertEquals(8.5, evaluation.getNote(), 0.01);
    }

}
