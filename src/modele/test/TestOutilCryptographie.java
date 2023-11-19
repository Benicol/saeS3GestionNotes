/*
 * TestOutilCryptographie.java                                           31 oct 2023
 * IUT de Rodez, pas de copyright
 */
package modele.test;

import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;

import modele.OutilCryptographie;

/** 
 * Classe de test de la classe outilCryptographie.java
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class TestOutilCryptographie {
    
    private static final int a = 17;
    private static final int b = 4;

    /** 
     * Test de la methode encoder() de la classe outilCryptographie
     */
    @Test
    public void testEncoder() {
        String cle = "tests junits";
        String[] messagesValides = {"Hello, world!", "", "test"};
        String[] chiffrerValides = {"&î]\\1,jc§@\\Yf", "", " î$ "};
        String[] messagesInvalides = {"åøØøå", "J'aime l'argent 💸", "57×39"};
        try {
            for (int i = 0; i < messagesValides.length; i++) {
                assertEquals(OutilCryptographie.encoder(cle, messagesValides[i]), chiffrerValides[i]);
            }
            for (String elt : messagesInvalides) {
                assertThrows(IllegalArgumentException.class, () -> OutilCryptographie.encoder(cle, elt));
            }
        } catch (Exception e) {
            System.err.println("erreur du Scanner");
        }
        
    }
    
    /** 
     * Test de la methode decoder() de la classe outilCryptographie
     */
    @Test
    public void testDecoder() {
        String cle = "tests junits";
        String[] chiffrementsValides = {"&î]\\1,jc§@\\Yf", "", " î$ "};
        String[] chiffrementsInvalides = {"fghgåøØøåhfgjhk²", "gdfgb,;:!:!💸:;", "45jogji×cxfh7"};
        String[] messagesValides = {"Hello, world!", "", "test"};
        try {
            for (int i = 0; i < chiffrementsValides.length; i++) {
                assertEquals(OutilCryptographie.decoder(cle, chiffrementsValides[i]), messagesValides[i]);
            }
            for (String elt : chiffrementsInvalides) {
                assertThrows(IllegalArgumentException.class, () -> OutilCryptographie.decoder(cle, elt));
            }
        } catch (Exception e) {
            System.err.println("erreur du Scanner");
        }
    }
    
    /** 
     * Test de la méthode creerCleVigenere() de la classe OutilCryptographie
     */
    @Test
    public void testCreerCleVigenere() {
        char[] alphabet = OutilCryptographie.getAlphabet().toCharArray();
        ArrayList<Character> listeCaracteres = new ArrayList<Character>();
        for (char c : alphabet) {
            listeCaracteres.add(c);
        }
        for (int i = 0; i < 10; i++) {
            String cle = OutilCryptographie.creerCleVigenere();
            for (int j = 0; j < cle.length(); j++) {
                assertTrue(listeCaracteres.contains(cle.charAt(j)));
            }
        }
    }
    
    /**
     * Test de la méthode coderCle() de la classe OutilCryptographie
     */
    @Test
    public void testCoderCle() {
        String cle = "3Êtw¤¨ €?9²§~^j#²éç’";
        BigInteger cle_codee = new BigInteger("768110624352840053260847821400351269520621326734400756992004025");
        assertEquals(OutilCryptographie.coderCle(cle, a, b), cle_codee);
    }
    
    /**
     * Test de la méthode decoderCle() de la classe OutilCryptographie
     */
    @Test
    public void testDecoderCle() {
        BigInteger cle_codee = new BigInteger("768110624352840053260847821400351269520621326734400756992004025");
        String cle = "3Êtw¤¨ €?9²§~^j#²éç’";
        assertEquals(OutilCryptographie.decoderCle(cle_codee, a, b), cle);
    }
}