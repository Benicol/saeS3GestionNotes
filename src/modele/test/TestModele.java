/*
 * TestModele.java                                           31 oct 2023
 * IUT de Rodez, pas de copyright
 */
package modele.test;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;

import modele.OutilFichier;
import modele.Parametrage;
import modele.Utilisateur;
import modele.Ressource;
import modele.Sae;
import modele.Competence;
import modele.OutilCSV;
import modele.Modele;

/** 
 * Classe de test de la classe outilCryptographie.java
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class TestModele {
	
	private Parametrage parametrage;
	private Parametrage parametrage2;
	
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
        
        String[][][] donneesCompetences2 = {{
								            {"C2.1","Compétence de test 1"},
								            {"R2.01", "60"}, 
								            {"S2.01", "38"}, 
								            
								            },
								           {
								            {"C2.2","Compétence de test 2"},
								            {"R2.01", "30"},
								            {"R2.02", "30"},
								            {"S2.02", "38"}, 
							
								           }};
        
        HashMap<String, String> donneesSaes = new HashMap<>();
        donneesSaes.put("S2.01", "sae de test 1");
        donneesSaes.put("S2.02", "sae de test 2");
        donneesSaes.put("P2.01", "Portfolio");
        
        HashMap<String, String> donneesSaes2 = new HashMap<>();
        donneesSaes2.put("S2.01", "sae de test 1");
        donneesSaes2.put("S2.02", "sae de test 2");
        
        HashMap<String, String> donneesRessources = new HashMap<>();
        donneesRessources.put("R2.01", "ressource de test 1");
        donneesRessources.put("R2.02", "ressource de test 2");
        
        HashMap<String, String> donneesRessources2 = new HashMap<>();
        donneesRessources2.put("R2.01", "ressource de test 1");
        
        
        parametrage = new Parametrage(semestre, parcours, donneesCompetences, donneesSaes, donneesRessources);
        parametrage2 = new Parametrage(semestre, parcours, donneesCompetences2, donneesSaes2, donneesRessources2);
        
        Modele.setParametrage(parametrage);
    }
	
    /** 
     * Test de la méthode verifierFormatDonnees() de la classe Modèle
     */
    @Test
    public void testVerifierFormatDonnees() {
        //Préparaion
        String fichier = OutilFichier.lire(".\\src\\modele\\test\\testModeleParametrage.csv");
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
    

    @Test
    public void testGetListeRessources() {
        HashMap<String, Ressource> ressources = parametrage.getListeRessources();
        HashMap<String, Ressource> ressources2 = parametrage2.getListeRessources();
        assertEquals(ressources, Modele.getRessources());
        assertNotEquals(ressources2, Modele.getRessources());
    }
    
    @Test
    public void testGetListeCompetences() {
        HashMap<String, Competence> competence = parametrage.getListeCompetences();
        HashMap<String, Competence> competence2 = parametrage2.getListeCompetences();
        assertEquals(competence, Modele.getCompetences());
        assertNotEquals(competence2, Modele.getCompetences());
    }
    
    @Test
    public void testGetListeSae() {
        HashMap<String, Sae > sae = parametrage.getListeSaes();
        HashMap<String, Sae > sae2 = parametrage2.getListeSaes();
        assertEquals(sae, Modele.getSae());
        assertNotEquals(sae2, Modele.getSae());
    }
    
    @Test
    public void testGetUtilisateur() {
        Utilisateur user = new Utilisateur();
        assertEquals(user.getPseudo(), Modele.getUtilisateur().getPseudo());
        assertNotEquals(null, Modele.getUtilisateur());
    }
    
    
    
}
