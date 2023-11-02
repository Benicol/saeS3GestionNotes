/*
 * TestOutilFichier.java                                      31 Oct 2023
 * IUT Rodez, pas de copyright ni "copyleft" 
 */
package modele.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import modele.OutilFichier;

/**
 * Tests en JUnit de la classe "OutilFichier".
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class TestOutilFichier {

    /* Déclaration des variables */
    private String cheminValide = "D:\\fichier.txt";
    private String cheminInvalide = "chemin/invalide/vers/le/fichier.txt";
    private File fichier;
    
    /**
     * Création d'un fichier avant l'execution de chaque test.
     * @throws java.lang.Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Crée le fichier avant chaque test
        fichier = new File(cheminValide);
        try {
            fichier.createNewFile();
        } catch (IOException e) {
            fail("Erreur lors de la création du fichier : " + e.getMessage());
        }
    }

    /**
     * Suppression du fichier précédemment crée après l'exécution de chaque test.
     * @throws java.lang.Exception
     */
    @AfterEach
    public void tearDown() throws Exception {
        // Supprime le fichier après chaque test
        if (fichier.exists()) {
            fichier.delete();
        }
    }

    /**
     * Test de la méthode ecrire() de la classe OutilFichier. 
     */
    @Test
    public void testEcrire() {
        /* TEST AVEC CHEMIN VALIDE */
        String contenuAEcrire = "Contenu du fichier à écrire.\n";

        boolean resultat = OutilFichier.ecrire(cheminValide, contenuAEcrire);

        assertTrue(resultat);

        // Vérifier le contenu du fichier
        String contenuFichier = OutilFichier.lire(cheminValide);
        assertEquals(contenuAEcrire, contenuFichier);
        
        /* TEST AVEC CHEMIN INVALIDE */
        resultat = OutilFichier.ecrire(cheminInvalide, contenuAEcrire);
        assertFalse(resultat);
    }

    /**
     * Test de la méthode lire() de OutilFichier.
     */
    @Test
    public void testLire() {
        /* TEST AVEC CHEMIN VALIDE */
        String contenuAttendu = "Contenu du fichier\nLigne 2\nLigne 3\n";
        OutilFichier.ecrire(cheminValide, contenuAttendu);

        String contenuLu = OutilFichier.lire(cheminValide);

        assertEquals(contenuAttendu, contenuLu);
        
        /* TEST AVEC CHEMIN INVALIDE */
        contenuLu = OutilFichier.lire(cheminInvalide);
        assertEquals("Une erreur s'est produite lors de la lecture du fichier : " 
                   + "fichier introuvable", contenuLu);
    }

}
