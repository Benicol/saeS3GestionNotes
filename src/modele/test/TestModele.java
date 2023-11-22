/*
 * TestModele.java                                           31 oct 2023
 * IUT de Rodez, pas de copyright
 */
package modele.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import modele.OutilFichier;
import modele.Parametrage;
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
	public void testExporter() {
		Modele.reset();
		Modele.importer(".\\src\\modele\\test\\csv\\testModeleParametrage.csv");
		assertDoesNotThrow(() -> Modele.exporter());
		OutilFichier.ecrire("test.csv", OutilCSV.formaterToCSV(Modele.exporter()));
		Modele.reset();
		
		Modele.importer("test.csv");
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
		
	
	}
	
	/**
	 * Test de la méthode importer
	 */
	@Test
	public void testImporter() {
	    Modele.reset();
	    //Modele.importer(".\\src\\modele\\test\\testModeleParametrage.csv");
        assertDoesNotThrow(() -> Modele.importer(".\\src\\modele\\test\\csv\\testModeleParametrage.csv"));
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
                () -> Modele.importer(".\\src\\modele\\test\\csv\\testModeleParametrage.csv"));
        Modele.reset();
        assertThrows(IllegalArgumentException.class,
                () -> Modele.importer(".\\src\\modele\\test\\csv\\fichierEvidemmentInvalide.csv"));
        Modele.reset();
        
        // tests sans semestre
        assertThrows(IllegalArgumentException.class, () -> Modele.importer(".\\src\\modele\\test\\csv\\testModeleParametrageInvalide1.csv"));
        Modele.reset();
        
        // tests sans parcours
        assertThrows(IllegalArgumentException.class, () -> Modele.importer(".\\src\\modele\\test\\csv\\testModeleParametrageInvalide2.csv"));
        
        // tests sans ligne vide entre 2 tableaux
        assertThrows(IllegalArgumentException.class, () -> {
            Modele.importer(".\\src\\modele\\test\\csv\\testModeleParametrageInvalide3.csv");
        });
        
        // tests avec un tableau qui ne commence pas par ressource ou compétence
        assertThrows(IllegalArgumentException.class, () -> {
            Modele.importer(".\\src\\modele\\test\\csv\\testModeleParametrageInvalide4.csv");
        });
        
        // tests où la pondération s'aditionne pas à 100
        assertThrows(IllegalArgumentException.class, () -> {
            Modele.importer(".\\src\\modele\\test\\csv\\testModeleParametrageInvalide5.csv");
        });
        
        // tests avec un identifiant invalide
        assertThrows(IllegalArgumentException.class, () -> {
            Modele.importer(".\\src\\modele\\test\\csv\\testModeleParametrageInvalide6.csv");
        });
        
        // tests avec le titre d'une ressource vide
        assertThrows(IllegalArgumentException.class, () -> {
            Modele.importer(".\\src\\modele\\test\\csv\\testModeleParametrageInvalide7.csv");
        });
        
        // tests avec un élément d'une competence qui n'a pas de nom
        assertThrows(IllegalArgumentException.class, () -> {
            Modele.importer(".\\src\\modele\\test\\csv\\testModeleParametrageInvalide8.csv");
        });
	}
	
	/**
     * Test de la méthode importerReseau
     */
    @Test
    public void testImporterReseau() {
        String chemin = ".\\src\\modele\\test\\csv\\";
        /* INSERTION DES DONNEES COMPATIBLES */
        Modele.reset();
        Modele.importer(".\\src\\modele\\test\\testModeleParametrage.csv");
        // Quand on importe le même paramétrage
        assertDoesNotThrow(
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin 
                                + "testModeleParametrage.csv"))));
        
        /* 
         * Quand on ajoute des dates (précédemment non renseignées) aux 
         * modalités d'évaluations
         */
        assertEquals("", 
                Modele.getParametrage().getListeRessources()
                .get("R2.02").getListeEvaluations().get(0).getDate());
        assertDoesNotThrow(() -> Modele.importerReseau(
                OutilCSV.formaterToDonnees(OutilFichier.lire(chemin + 
                        "testModeleParametrageReseauDateModifiee.csv"))));
        assertEquals("DATE MODIFIEE", 
                Modele.getParametrage().getListeRessources()
                .get("R2.02").getListeEvaluations().get(0).getDate());
        
        /* 
         * Quand on ajoute des modalités d'évaluations qui n'étaient 
         * pas paramétrées 
         */
        Modele.reset();
        Modele.importer(".\\src\\modele\\test\\testModeleParametrage.csv");
        assertDoesNotThrow(
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                       OutilFichier.lire(chemin 
                       + "testModeleParametrageReseauNouvelleRessource.csv"))));
        
        // Quand on importe via le réseau alors que le paramétrage est vide
        Modele.reset();
        assertDoesNotThrow(
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin
                                + "testModeleParametrage.csv"))));

        /* INSERTION DES DONNEES INCOMPATIBLES */
        // Quand on importe un fichier qui ne correspond pas au semestre
        assertThrows(IllegalArgumentException.class, 
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin 
                               + "testModeleParametrageReseauInvalide1.csv"))));
        // Quand on importe un fichier qui ne correspond pas au parcours
        assertThrows(IllegalArgumentException.class, 
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin 
                               + "testModeleParametrageReseauInvalide2.csv"))));
        /* 
         * Quand on importe un fichier où les identifiants, libellés ou poids 
         * des compétences et des ressources  ne sont pas identiques
         */
        assertThrows(IllegalArgumentException.class, 
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin + 
                                "testModeleParametrageReseauInvalide3.csv"))));
        assertThrows(IllegalArgumentException.class, 
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin + 
                                "testModeleParametrageReseauInvalide4.csv"))));
        assertThrows(IllegalArgumentException.class, 
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin
                               + "testModeleParametrageReseauInvalide5.csv"))));
        assertThrows(IllegalArgumentException.class, 
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin 
                               + "testModeleParametrageReseauInvalide6.csv"))));
        assertThrows(IllegalArgumentException.class, 
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin
                               + "testModeleParametrageReseauInvalide7.csv"))));
        
        /* 
         * Quand on importe un fichier où les identifiants, libellés ou poids 
         * des évaluations  ne sont pas identiques
         */
        assertThrows(IllegalArgumentException.class, 
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin 
                               + "testModeleParametrageReseauInvalide8.csv"))));
        assertThrows(IllegalArgumentException.class, 
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin 
                               + "testModeleParametrageReseauInvalide9.csv"))));
        assertThrows(IllegalArgumentException.class, 
                () -> Modele.importerReseau(OutilCSV.formaterToDonnees(
                        OutilFichier.lire(chemin
                              + "testModeleParametrageReseauInvalide10.csv"))));

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
        Modele.getUtilisateur().setPseudo("Utilisateur");
        assertEquals("Utilisateur", Modele.getUtilisateur().getPseudo());
        assertNotEquals(null, Modele.getUtilisateur());
    }
}
