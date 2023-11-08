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
public class TestEvaluation {

    private Evaluation evaluation;

    /**
     * Création d'une évaluation avant l'execution de chaque test.
     */
    @BeforeEach
    public void setUp() {
        // Initialisation avant chaque test
        evaluation = new Evaluation("Examen de math", 0.4, "15/11/2023");
    }

    /**
     * Teste le constructeur de la classe Evaluation.
     */
    @Test
    public void testConstructeur() {
        assertEquals("Examen de math", evaluation.getNom());
        assertEquals(0.4, evaluation.getPoids());
        assertEquals("15/11/2023", evaluation.getDate());
        assertNull(evaluation.getNote());
    }

    /**
     * Teste les méthodes setters de la classe Evaluation.
     */
    @Test
    public void testSetters() {
        evaluation.setNom("Devoir d'histoire");
        assertEquals("Devoir d'histoire", evaluation.getNom());

        evaluation.setPoids(0.3);
        assertEquals(0.3, evaluation.getPoids());

        evaluation.setDate("20/11/2023");
        assertEquals("20/11/2023", evaluation.getDate());

        evaluation.setNote(18.5);
        assertEquals(18.5, evaluation.getNote(), 0.01); // Vérifie la note avec une tolérance de 0.01
    }

    /**
     * Teste les méthodes setters avec des valeurs hors limites.
     */
    @Test
    public void testSettersOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> evaluation.setNote(25.0));
        assertThrows(IllegalArgumentException.class, () -> evaluation.setNote(-5.0));
        assertThrows(IllegalArgumentException.class, () -> evaluation.setPoids(1.5));
        assertThrows(IllegalArgumentException.class, () -> evaluation.setPoids(-1.0));
    }

    /**
     * Teste les méthodes setters avec des valeurs invalides.
     */
    @Test
    public void testSettersInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> evaluation.setNom(""));
        assertThrows(IllegalArgumentException.class, () -> evaluation.setDate(""));
    }
}
