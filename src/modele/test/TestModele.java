/*
 * TestModele.java                                           31 oct 2023
 * IUT de Rodez, pas de copyright
 */
package modele.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.HashMap;

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
                                            {"R2.01", "0.6"}, 
                                            {"S2.01", "0.38"}, 
                                            {"P2.01", "0.2"}
                                            },
                                           {
                                            {"C2.2","Compétence de test 2"},
                                            {"R2.01", "0.30"},
                                            {"R2.02", "0.30"},
                                            {"S2.02", "0.38"}, 
                                            {"P2.01", "0.2"}
                                           }};
        
        String[][][] donneesCompetences2 = {{
								            {"C2.1","Compétence de test 1"},
								            {"R2.01", "0.60"}, 
								            {"S2.01", "0.38"}, 
								            
								            },
								           {
								            {"C2.2","Compétence de test 2"},
								            {"R2.01", "0.30"},
								            {"R2.02", "0.30"},
								            {"S2.02", "0.38"}, 
							
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
	 * Test de la méthode 
	 */
	@Test
	public void testImporter() {
	    Modele.reset();
	    //Modele.importer(".\\src\\modele\\test\\testModeleParametrage.csv");
        assertDoesNotThrow(() -> Modele.importer(".\\src\\modele\\test\\testModeleParametrage.csv"));
        assertEquals("2", Modele.getParametrage().getSemestre());
        assertEquals("Tous", Modele.getParametrage().getParcours());
        assertEquals(6, Modele.getCompetences().size());
        assertEquals(14, Modele.getRessources().size());
        assertEquals(7, Modele.getSae().size());
        assertEquals("U2.1",Modele.getCompetences().get("U2.1").getIdentifiant());
        assertEquals("Réaliser un développement d’application",Modele.getCompetences().get("U2.1").getLibelle());
        assertEquals(4,Modele.getCompetences().get("U2.1").getListeRessources().size());
        assertEquals(2,Modele.getCompetences().get("U2.1").getListeSaes().size());
        assertEquals(4,Modele.getRessources().get("R2.01").getListeEvaluations().size());
        assertEquals(0,Modele.getRessources().get("R2.03").getListeEvaluations().size());
        assertThrows(IllegalArgumentException.class,
                () -> Modele.importer(".\\src\\modele\\test\\testModeleParametrage.csv"));
        Modele.reset();
        assertThrows(IllegalArgumentException.class,
                () -> Modele.importer(".\\src\\modele\\test\\fichierEvidemmentInvalide.csv"));
        
        
        
	}
	
	/**
	 * Test de la méthode isParametrageInitialise()
	 */
	@Test
	public void testIsParamaetrageInitialises() {
	    assertTrue(Modele.isParametrageInitialise());
	    Modele.reset();
	    assertFalse(Modele.isParametrageInitialise());
	}
	
    /** 
     * Test de la méthode verifierFormatDonnees() de la classe Modèle
     */
    @Test
    public void testVerifierFormatDonnees() {
        //Préparation
        String fichier = OutilFichier.lire(".\\src\\modele\\test\\testModeleParametrage.csv");
        String[][] donneesTests = OutilCSV.formaterToDonnees(fichier);
        String[][] donneesInvalides = donneesTests;
        
        // test conditions normales
        assertTrue(Modele.verifierFormatDonnees(donneesTests)); 
        
        // tests sans semestre
        donneesInvalides[1][1] = "";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        
        // tests sans parcours
        donneesInvalides[2][0] = "un truc là";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        
        // tests sans ligne vide entre 2 tableaux
        donneesInvalides[3] = new String[1];
        donneesInvalides[3][0] = "COUCOU C'EST MOI";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        
        // tests avec un tableau qui ne commence pas par ressource ou compétence
        donneesInvalides[13][0] = "SAE";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        
        // tests où la pondération s'aditionne pas à 100
        donneesInvalides[17][0] = "196";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        
        // tests avec un identifiant invalide
        donneesInvalides[24][1] = "R2.4";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        
        // tests avec le titre d'une ressource vide
        donneesInvalides[31][2] = "";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        
        // tests avec un élément d'une competence qui n'a pas de nom
        donneesInvalides[27][0] = "";
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
        
        // tests avec un identifiant null
        donneesInvalides[36][1] = null;
        assertFalse(Modele.verifierFormatDonnees(donneesInvalides));
        donneesInvalides = donneesTests;
    }
    

    /**
     * test de la methode getListRessources()
     */
    @Test
    public void testGetListeRessources() {
        HashMap<String, Ressource> ressources = parametrage.getListeRessources();
        HashMap<String, Ressource> ressources2 = parametrage2.getListeRessources();
        assertEquals(ressources, Modele.getRessources());
        assertNotEquals(ressources2, Modele.getRessources());
    }
    
    /**
     * test de la methode getListeCompetences
     */
    @Test
    public void testGetListeCompetences() {
        HashMap<String, Competence> competence = parametrage.getListeCompetences();
        HashMap<String, Competence> competence2 = parametrage2.getListeCompetences();
        assertEquals(competence, Modele.getCompetences());
        assertNotEquals(competence2, Modele.getCompetences());
    }
    
    /**
     * test de la methode getListeSae
     */
    @Test
    public void testGetListeSae() {
        HashMap<String, Sae > sae = parametrage.getListeSaes();
        HashMap<String, Sae > sae2 = parametrage2.getListeSaes();
        assertEquals(sae, Modele.getSae());
        assertNotEquals(sae2, Modele.getSae());
    }
    
    /**
     * test de la methode getUtilisateur
     */
    @Test
    public void testGetUtilisateur() {
        Utilisateur user = new Utilisateur();
        assertEquals(user.getPseudo(), Modele.getUtilisateur().getPseudo());
        assertNotEquals(null, Modele.getUtilisateur());
    }
    
    
    

}
