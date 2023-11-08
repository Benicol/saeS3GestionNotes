/*
 * TestUtilisateur.java                                               21/10/2023
 * IUT de Rodez, pas de droit d'auteur
 */
package modele.test;
import modele.Utilisateur;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

/**
 * Classe de test de la classe Utilisateur.
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class TestUtilisateur {
    
    /* Tableau d'utilisateur utilisée pour tester les méthodes */
    private Utilisateur[] utilisateurs = new Utilisateur[5];
    
    /** 
     * Crée un tableau avant chaque test comportant
     * 5 Utilisateur avec les pseudos : 
     * ["", "testing", "jean michel", null, "Utilisateur"]
     */
    @BeforeEach
    public void setUp() {
        utilisateurs[0] = new Utilisateur("");
        utilisateurs[1] = new Utilisateur("testing");
        utilisateurs[2] = new Utilisateur("jean michel");
        utilisateurs[3] = new Utilisateur(null);
        utilisateurs[4] = new Utilisateur();
    }
    
    /** 
     * test la methode getPseudo de Utilisateur
     */
    @Test
    public void testGetPseudo() {
        assertEquals("Utilisateur", utilisateurs[0].getPseudo());
        assertEquals("testing", utilisateurs[1].getPseudo());
        assertEquals("jean michel", utilisateurs[2].getPseudo());
        assertEquals("Utilisateur", utilisateurs[3].getPseudo());
        assertEquals("Utilisateur", utilisateurs[4].getPseudo());
    }

    /** 
     * test la methode setPseudo de Utilisateur
     */
    @Test
    public void testSetPseudo() {
        utilisateurs[0].setPseudo("test1");
        utilisateurs[1].setPseudo("");
        utilisateurs[2].setPseudo(null);
        utilisateurs[3].setPseudo("test2");
        utilisateurs[4].setPseudo("coucou");
        assertEquals("test1", utilisateurs[0].getPseudo());
        assertEquals("testing", utilisateurs[1].getPseudo());
        assertEquals("jean michel", utilisateurs[2].getPseudo());
        assertEquals("test2", utilisateurs[3].getPseudo());
        assertEquals("coucou", utilisateurs[4].getPseudo());
    }
}
