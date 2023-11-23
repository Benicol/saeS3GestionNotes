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
    private static final int P = 9739;
    private static final int G = 1527;
    private static final int A = 17;
    private static final int B = 4;

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
                assertEquals(OutilCryptographie.encoder(cle, messagesValides[i]),
                        chiffrerValides[i]);
            }
            for (String elt : messagesInvalides) {
                assertThrows(IllegalArgumentException.class, 
                        () -> OutilCryptographie.encoder(cle, elt));
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
        String[] chiffrementsInvalides = {"fghgåøØøåhfgjhk²", "gdfgb,;:!:!💸:;",
                "45jogji×cxfh7"};
        String[] messagesValides = {"Hello, world!", "", "test"};
        try {
            for (int i = 0; i < chiffrementsValides.length; i++) {
                assertEquals(OutilCryptographie.decoder(cle,
                        chiffrementsValides[i]), messagesValides[i]);
            }
            for (String elt : chiffrementsInvalides) {
                assertThrows(IllegalArgumentException.class,
                        () -> OutilCryptographie.decoder(cle, elt));
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
        BigInteger gb = new BigInteger(Integer.toString(G)).pow(B);
        String cle = "4Êtw£° $@²*~^`j0*éç.";
        BigInteger cle_codee = new BigInteger("60882158449929312843767150952411"
                + "1765773571052896181357932355128");
        assertEquals(OutilCryptographie.coderCle(cle, A, gb, P), cle_codee);
    }
    
    /**
     * Test de la méthode decoderCle() de la classe OutilCryptographie
     */
    @Test
    public void testDecoderCle() {
        BigInteger ga = new BigInteger(Integer.toString(G)).pow(A);
        BigInteger cle_codee = new BigInteger("60882158449929312843767150952411"
                + "1765773571052896181357932355128");
        String cle = "4Êtw£° $@²*~^`j0*éç.";
        assertEquals(OutilCryptographie.decoderCle(cle_codee, ga, B, P), cle);
    }
    
    /**
     * Test de la méthode genererAB() de la classe OutilCryptographie
     */
    @Test
    public void testGenererAB() {
        int ab = OutilCryptographie.genererAB(P);
        assertTrue(ab >= 0 && ab <= Math.sqrt(P));
    }
    
    /**
     * Test de la méthode genererP() de la classe OutilCryptographie
     */
    @Test
    public void testGenererP() {
        int p = OutilCryptographie.genererP();
        assertTrue(p >= 1000 && p <= 1000000);
    }
    
    /**
     * Test de la méthode genererG() de la classe OutilCryptographie
     */
    @Test
    public void testGenererG() {
        int g1 = OutilCryptographie.genererG(5);
        int g2 = OutilCryptographie.genererG(7);
        assertTrue(g1 == 2 || g1 == 3);
        assertTrue(g2 == 3 || g2 == 5);
    }
}