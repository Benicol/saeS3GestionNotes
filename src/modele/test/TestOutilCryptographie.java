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
    
    private static final BigInteger p = new BigInteger("9739");
    private static final BigInteger g = new BigInteger("1527");
    private static final BigInteger a = new BigInteger("17");
    private static final BigInteger b = new BigInteger("4");

    /** 
     * Test de la methode encoder() de la classe outilCryptographie
     */
    @Test
    public void testEncoder() {
        String cle = "tests junits";
        String[] messagesValides = {"Hello, world!", "", "test"};
        String[] chiffrerValides = {"@ì[/0'ïc&?/XF", "", " ì€ "};
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
        String[] chiffrementsValides = {"@ì[/0'ïc&?/XF", "", " ì€ "};
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
        BigInteger gb = g.pow(b.intValue());
        String cle = "4Êtw£° $@²*~^`j0*éç.";
        BigInteger cle_codee = new BigInteger("604028584499288330644666711726313967975773259891383564932350335");
        assertEquals(OutilCryptographie.coderCle(cle, a, gb, p), cle_codee);
    }
    
    /**
     * Test de la méthode decoderCle() de la classe OutilCryptographie
     */
    @Test
    public void testDecoderCle() {
        BigInteger ga = g.pow(a.intValue());
        BigInteger cle_codee = new BigInteger("604028584499288330644666711726313967975773259891383564932350335");
        String cle = "4Êtw£° $@²*~^`j0*éç.";
        assertEquals(OutilCryptographie.decoderCle(cle_codee, ga, b, p), cle);
    }
}