/*
 * OutilCryptographie.java                                               31 oct 2023                                                                                21/10/2015
 * IUT de Rodez, pas de copyright
 */

package modele;

import java.math.BigInteger;

/** Classe outil permettant de : 
 * - encoder un message à l'aide de l'algorithme de Vigenère et une clé fournie, 
 * en utilisant l'alphabet de la classe.
 * - decoder un message à l'aide de l'algorithme de Vigenère et une clé fournie, 
 * en utilisant l'alphabet de la classe.
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class OutilCryptographie {
    
    /* Alphabet utilisé pour le chiffrement correspondant à
     * tous les caractères présents sur un clavier Azerty.*/
    private static final String alphabet = " AaÀàÂâÄäÃãBbCcçDdEeéÈèÊêËëFfGgHhI"
                                         + "iÌìÎîÏïJjKkLlMmNnÑñOoÒòÔôÖöÕõPpQqR"
                                         + "rSsTtUuÙùÛûÜüVvWwXxYyÿZz_-\'.,;:!?@"
                                         + "&§~^`¨°|(){}[]/\\<>\"#0123456789²*"
                                         + "+=%µ€$¤£";
    private static final int p = 9739;
    private static final int g = 1527;
    
    /**
     * Méthode qui permet de chiffrer un message.
     * @param cle la clé de chiffrement.
     * @param message le message à chiffrer.
     * @return le message chiffré.
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
                throw new IllegalArgumentException(c + " ne fait pas partie de "
                                                     + "l'alphabet autorisé");
            }
        }
        return sb.toString();
    }

    /**
     * Méthode qui permet de dechiffrer un message.
     * @param cle la clé de déchiffrement.
     * @param message le message a déchiffrer.
     * @return le message dechiffré.
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
                throw new IllegalArgumentException(c + " ne fait pas partie de "
                                                     + "l'alphabet autorisé");
            }
        }
        return sb.toString();
    }

    /**
     * Vérifie si un caractère est valide, c'est à dire qu'il est présent dans
     * l'alphabet de la classe.
     * @param c caractère à vérifier.
     * @return true si le caractère est valide, false sinon.
     */
    private static boolean isCaractereValide(char c) {
        return alphabet.indexOf(c) != -1;
    }

    /**
     * Classe permettant de connaître quel est le décalage pour le caractère à 
     * l'index donné.
     * @param index Index du caractère dans la clé.
     * @return Decalage le décalage pour un caractère.
     */
    private static int getDecalage(String cle, int index) {
        char c = cle.charAt(index);
        int decalage = alphabet.indexOf(c) - alphabet.indexOf('a');
        return decalage;
    }

    /**
     * Méthode permettant d'encoder un caractère selon le décalage passé en 
     * paramètre.
     * @param c le caractère à encoder.
     * @param decalage le nombre associé au décalage du caractère.
     * @return le caractère une fois encodé en fonction du décalage.
     */
    private static char encoderCaractere(char c, int decalage) {
        int index = alphabet.indexOf(c);
        int indexEncode = (index + decalage) % alphabet.length();
        return alphabet.charAt(indexEncode);
    }

    /**
     * Méthode permettant de décoder un caractère selon le décalage passé en 
     * paramètre.
     * @param c le caractère à décoder.
     * @param decalage le nombre associé au décalage du caractère (pour retrouver 
     * le caractère d'origine).
     * @return le caractère une fois décodé en fonction du décalage.
     */
    private static char decoderCaractere(char c, int decalage) {
        int index = alphabet.indexOf(c);
        int indexDecode = (index - decalage + alphabet.length()) % alphabet.length();
        return alphabet.charAt(indexDecode);
    }
    
    /** Getter de l'attribut alphabet
     * @return alphabet
     */
    public static String getAlphabet() {
        return alphabet;
    }
    
    /** Permet de générer aléatoirement une clé de cryptage pour Vigenere
     * @return cle
     */
    public static String creerCleVigenere() {
        StringBuilder cle = new StringBuilder();
        
        int index = 0;
        index = (int)(Math.random() * (alphabet.length() - 100) + 100);
        cle.append(alphabet.charAt(index));
        
        for (int i = 1; i < 20; i++) {
            index = (int)(Math.random() * alphabet.length());
            cle.append(alphabet.charAt(index));
        }
        return cle.toString();
    }
    
    /** Méthode permettant de coder la clé de l'algorithme de Vigenère
     * afin de l'envoyer via l'échange de Diffie-Hellman
     * @param cle 
     * @param a
     * @param b
     * @return cle_codee
     */
    public static BigInteger coderCle(String cle, int a, int b) {
        StringBuilder cle_codee_str = new StringBuilder();
        int index = 0;
        for (int i = 0; i < 20; i++) {
            index = alphabet.indexOf(cle.charAt(i));
            cle_codee_str.append(String.format("%03d", index));
        }
        
        BigInteger cle_codee = new BigInteger(cle_codee_str.toString());
        int code = (int)(Math.pow(g, (a*b)) % p);
        cle_codee = cle_codee.multiply(BigInteger.valueOf(code));
        return cle_codee;
    }
    
    /** Méthode permettant de décoder la clé de l'algorithme de Vigenère
     * reçue avec l'échange de Diffie-Hellman
     * @param cle_codee
     * @param a
     * @param b
     * @return cle_decodee
     */
    public static String decoderCle(BigInteger cle_codee, int a, int b) {
        int code = (int)(Math.pow(g, (a*b)) % p);
        BigInteger cle_codee_divisee = cle_codee.divide(BigInteger.valueOf(code));
        
        int index = 0;
        int compteur = 0;
        String cle_codee_str = cle_codee_divisee.toString();
        StringBuilder cle_decodee = new StringBuilder();
        for (int i = 0; i < 20; i ++) {
            index = Integer.parseInt("" + cle_codee_str.charAt(compteur) + cle_codee_str.charAt(compteur + 1) + cle_codee_str.charAt(compteur + 2));
            cle_decodee.append(alphabet.charAt(index));
            compteur += 3;
        }
        return cle_decodee.toString();
    }
}