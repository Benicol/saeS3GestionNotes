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
    private static final BigInteger P = new BigInteger("9739");
    private static final BigInteger G = new BigInteger("1527");
    private static final BigInteger A = new BigInteger("17");
    private static final BigInteger B = new BigInteger("4");

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
        BigInteger gb = G.pow(B.intValue());
        String cle = "4Êtw£° $@²*~^`j0*éç.";
        BigInteger cle_codee = new BigInteger("608821584499293128437671509524111765773571052896181357932355128");
        System.out.println(OutilCryptographie.coderCle(cle, A, gb, P));
        assertEquals(OutilCryptographie.coderCle(cle, A, gb, P), cle_codee);
    }
    
    /**
     * Test de la méthode decoderCle() de la classe OutilCryptographie
     */
    @Test
    public void testDecoderCle() {
        BigInteger ga = G.pow(A.intValue());
        BigInteger cle_codee = new BigInteger("608821584499293128437671509524111765773571052896181357932355128");
        String cle = "4Êtw£° $@²*~^`j0*éç.";
        //System.out.println(OutilCryptographie.decoderCle(cle_codee, ga, B, P));
        assertEquals(OutilCryptographie.decoderCle(cle_codee, ga, B, P), cle);
    }
    
    /**
     * Test de la méthode genererAB() de la classe OutilCryptographie
     */
    @Test
    public void testGenererAB() {
        BigInteger ab = OutilCryptographie.genererAB(P);
        assertTrue(ab.intValue() >= 0 && ab.intValue() <= Math.sqrt(P.intValue()));
    }
    
    /**
     * Test de la méthode genererP() de la classe OutilCryptographie
     */
    @Test
    public void testGenererP() {
        BigInteger p = OutilCryptographie.genererP();
        assertTrue(p.intValue() >= 1000 && p.intValue() <= 1000000);
        assertTrue(p.isProbablePrime(0));
    }
    
    /**
     * Test de la méthode genererG() de la classe OutilCryptographie
     */
    @Test
    public void testGenererG() {
        BigInteger g1 = OutilCryptographie.genererG(new BigInteger("5"));
        BigInteger g2 = OutilCryptographie.genererG(new BigInteger("7"));
        assertTrue(g1.intValue() == 2 || g1.intValue() == 3);
        assertTrue(g2.intValue() == 3 || g1.intValue() == 5);
    }
}