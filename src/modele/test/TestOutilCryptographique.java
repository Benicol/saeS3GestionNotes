/**
 * TestOutilCryptographie.java                                  31/10/2023
 * No copyright
 */
package modele.test;

import org.junit.Test;
import static org.junit.Assert.*;
import modele.OutilCryptographie;

/** classe de test de la classe outilCryptographie.java
 * @author Benjamin Nicol
 */
public class TestOutilCryptographique {

    /** 
     * test la methode encoder() de la classe outilCryptographie
     */
    @Test
    public void testEncoder() {
        String cle = "tests junits";
        String[] messagesValides = {"Hello, world!", "", "test"};
        String[] chiffrerValides = {"àiDEG.iQBzEvb", "", "MiKM"};
        String[] messagesInvalides = {"¤weee¤", "J'aime l'argent €", "57²*39+12"};
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
     * test la methode decoder() de la classe outilCryptographie
     */
    @Test
    public void testDecoder() {
        String cle = "tests junits";
        String[] chiffrementsValides = {"àiDEG.iQBzEvb", "", "MiKM"};
        String[] chiffrementsInvalides = {"fghghj@fghfgjhk²", "gdfgb,;:!:!§:;", "45jogji£cxfh7"};
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
}