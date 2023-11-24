/*
 * OutilCryptographie.java                                               31 oct 2023                                                                                21/10/2015
 * IUT de Rodez, pas de copyright
 */

package modele;

import java.math.BigInteger;
import java.util.TreeSet;

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
                                         + "rSsTtUuÙùÛûÜüVvWwXxYyÿZz_-'’.,;:!?@"
                                         + "&§~^`¨°|(){}[]/\\<>\"#0123456789²*"
                                         + "+=%µ€$¤£\n";
    
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
                sb.append(c);
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
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    /**
     * Classe permettant de connaître quel est le décalage pour le caractère à 
     * l'index donné.
     * @param index Index du caractère dans la clé.
     * @return Decalage le décalage pour un caractère.
     */
    private static int getDecalage(String cle, int index) {
        char c = cle.charAt(index);
        return alphabet.indexOf(c);
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
        int indexDecode = (index - decalage + (alphabet.length())) % (
                alphabet.length());
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
        int taille = (int)(Math.random() * 50) + 20;
        
        int index = (int)(Math.random() * (alphabet.length() - 100) + 100);
        cle.append(alphabet.charAt(index));
        
        for (int i = 1; i < taille; i++) {
            index = (int)(Math.random() * alphabet.length());
            cle.append(alphabet.charAt(index));
        }
        return cle.toString();
    }
    
    /** Méthode permettant de coder la clé de l'algorithme de Vigenère
     * afin de l'envoyer via l'échange de Diffie-Hellman
     * @param cle 
     * @param a entier choisi
     * @param gb entier reçu
     * @param p entier premier modulo
     * @return cle_codee
     */
    public static BigInteger coderCle(String cle, int a, BigInteger gb, int p) {
        StringBuilder cle_codee_str = new StringBuilder();
        int index = 0;
        for (int i = 0; i < cle.length(); i++) {
            index = alphabet.indexOf(cle.charAt(i));
            cle_codee_str.append(String.format("%03d", index));
        }
        
        BigInteger cle_codee = new BigInteger(cle_codee_str.toString());
        BigInteger code = gb.pow(a).mod(new BigInteger(Integer.toString(p)));
        cle_codee = cle_codee.multiply(code);
        return cle_codee;
    }
    
    /** Méthode permettant de décoder la clé de l'algorithme de Vigenère
     * reçue avec l'échange de Diffie-Hellman
     * @param cle_codee
     * @param ga entier reçu
     * @param b entier choisi
     * @param p entier premier modulo
     * @return cle_decodee
     */
    public static String decoderCle(BigInteger cle_codee, BigInteger ga, int b, int p) {
        BigInteger code = ga.pow(b).mod(new BigInteger(Integer.toString(p)));
        OutilFichier.ecrire("code.txt", code.toString());
        String test = code + "\n";
        BigInteger cle_codee_divisee = cle_codee.divide(code);
        test += cle_codee_divisee;
        OutilFichier.ecrire("idk.txt", test);
        
        
        int index = 0;
        String cle_codee_str = cle_codee_divisee.toString();
        StringBuilder cle_decodee = new StringBuilder();
        int taille_finale = cle_codee_str.length();
        for (int i = 0; i < taille_finale - 2; i+=3) {
            index = Integer.parseInt("" + cle_codee_str.charAt(i) 
            + cle_codee_str.charAt(i + 1) + cle_codee_str.charAt(i + 2));
            cle_decodee.append(alphabet.charAt(index));
        }
        return cle_decodee.toString();
    }
    
    /** 
     * Méthode permettant de générer a et b
     * @param p entier premier modulo
     * @return nombre généré
     */
    public static int genererAB(int p) {
        return (int)(Math.random() * Math.sqrt(p));
    }
    
    /** 
     * Méthode permettant de génerer aléatoirement
     * un entier premier p entre 1000 et 1000000
     * @return p un entier premier entre 1000 et 1000000
     */
    public static int genererP() {
        BigInteger nb = new BigInteger(
                Integer.toString((int)(Math.random() * 500000) + 1000));
        BigInteger p = nb.nextProbablePrime();
        return p.intValue();
    }
    
    /**
     * Méthode permettant de génerer aléàtoirement
     * un entier générateur g du modulo p
     * @param p un entier premier servant de modulo
     * @return g un entier générateur aléàtoire de p
     */
    public static int genererG(int p) {
        //Initialisation des variables
        TreeSet<Long> listeResultats = new TreeSet<>(); 
        boolean ajoutOk;
        long exposant;
        int g;
        boolean gTrouve = false;
        do {
            listeResultats.clear();
            ajoutOk = true;
            exposant = 1;
            g = (int)(Math.random() * p);
            //Boucle permettant de calculer tout les i exposant j
            //pour tout j entre 1 et p
            for (int j = 1 ; j < p && ajoutOk ; j++) {
                exposant = (exposant * g) % p;
                ajoutOk = listeResultats.add(exposant);
            }
            //Vérifie que i est un entier générateur de p,
            //et si oui, l'ajoute à listeG
            if (listeResultats.size() == (p - 1)) {
                gTrouve = true;
            }
        } while (!gTrouve);
        return g;
    }
}