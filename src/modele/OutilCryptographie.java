/**
 * OutilCryptographiz.java                                                                                 21/10/2015
 * No copyright.
 */

package modele;


/** Classe outil permettant de : 
 * - encoder un message a l'aide de l'algorithme de vigenere et une cle fournis, en utilisant l'alphabet de la classe
 * - decoder un message a l'aide de l'algorithme de vigenere et une cle fournis, en utilisant l'alphabet de la classe
 * @author Benjamin Nicol
 */
public class OutilCryptographie {
    /**
     * Alphabet utilise pour le chiffrement.
     */
    private static final String alphabet = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZàâæçéèêëîïôœùûüÿÀÂÆÇÉÈÊËÎÏÔŒÙÛÜŸ0123456789.,:;!?()[]{}'\"-/_=+*%";
    
    /**
     * Methode qui permet de chiffrer un message.
     * @param cle la cle de chiffrement.
     * @param message le message a chiffrer.
     * @return le message chiffre.
     */
    public static String encoder(String cle, String message) {
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (isCaractereValide(c)) {
                int decalage = getDecalage(cle, j);
                char cEncode = encoderCaractere(c, decalage);
                sb.append(cEncode);
                j = (j + 1) % cle.length();
            } else {
                throw new IllegalArgumentException("Un caractère de l'alphabet n'est pas dans l'alphabet");
            }
        }
        return sb.toString();
    }

    /**
     * Methode qui permet de dechiffrer un message.
     * @param cle la cle de dechiffrement.
     * @param message le message a dechiffrer.
     * @return le message dechiffre.
     */
    public static String decoder(String cle, String message) {
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (isCaractereValide(c)) {
                int decalage = getDecalage(cle, j);
                char cDecode = decoderCaractere(c, decalage);
                sb.append(cDecode);
                j = (j + 1) % cle.length();
            }
            else {
                throw new IllegalArgumentException("Un caractère de l'alphabet n'est pas dans l'alphabet");
            }
        }
        return sb.toString();
    }

    /**
     * Verifie si un caractere est valide
     * @param c Caractere a verifier
     * @return true si le caractere est valide, false sinon
     */
    private static boolean isCaractereValide(char c) {
        return alphabet.indexOf(c) != -1;
    }

    /**
     * Retourne le decalage pour un caractere
     * @param index Index du caractere dans la cle
     * @return Decalage
     */
    private static int getDecalage(String cle, int index) {
        char c = cle.charAt(index);
        int decalage = alphabet.indexOf(c) - alphabet.indexOf('a');
        return decalage;
    }

    /**
     * Encode un caractere
     * @param c Caractere a encoder
     * @param decalage Decalage
     * @return Caractere encode
     */
    private static char encoderCaractere(char c, int decalage) {
        int index = alphabet.indexOf(c);
        int indexEncode = (index + decalage) % alphabet.length();
        return alphabet.charAt(indexEncode);
    }

    /**
     * Decode un caractere
     * @param c Caractere a decoder
     * @param decalage Decalage
     * @return Caractere decode
     */
    private static char decoderCaractere(char c, int decalage) {
        int index = alphabet.indexOf(c);
        int indexDecode = (index - decalage + alphabet.length()) % alphabet.length();
        return alphabet.charAt(indexDecode);
    }
}