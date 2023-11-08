/*
 * TestModele.java                                           31 oct 2023
 * IUT de Rodez, pas de copyright
 */
package modele.test;

import org.junit.Test;
import static org.junit.Assert.*;
import modele.OutilFichier;
import modele.OutilCSV;
import modele.Modele;

/** 
 * Classe de test de la classe outilCryptographie.java
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class TestModele {
    /** 
     * Teste de la methode verifierFormatDonnees() de la classe Modele
     */
    @Test
    public void testVerifierFormatDonnees() {
        //Préparaion
        String fichier = OutilFichier.lire(".\\src\\modele\\test\\testSae.csv");
        System.out.println(fichier);
        String[][] donneesTests = OutilCSV.formaterToDonnees(fichier);
        String[][] donneesInvalides = donneesTests;
        //tests
        assertTrue(Modele.verifierFormatDonnees(donneesTests));
        //test où il n'y a pas de Semestre
        donneesInvalides[1][1] = "";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        //test où il n'y a pas de parcours
        donneesInvalides[2][0] = "un truc là";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        //test où il n'y a pas une ligne vide entre 2 tableaux
        donneesInvalides[3] = new String[1];
        donneesInvalides[3][0] = "COUCOU C'EST MOI";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        //test où il y a un tableau qui commence pas par ressource ou compétence
        donneesInvalides[13][0] = "SAE";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        //test où la pondération s'aditionne pas à 100
        donneesInvalides[17][0] = "196";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        //test avec un identifiant invalide
        donneesInvalides[24][1] = "R2.4";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        //test avec le titre d'une ressource vide
        donneesInvalides[31][2] = "";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        //test avec un element d'une competence qui n'as pas de nom
        donneesInvalides[27][0] = "";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        //test où un identifiant est null
        donneesInvalides[36][1] = null;
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
    }
}
