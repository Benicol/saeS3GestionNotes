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
        /* Boucle qui code le message */
        for (int i = 0; i < message.length(); i++) {
        	/* On récupère le i emes carctère du message à coder*/
            char c = message.charAt(i);
            if (isCaractereValide(c)) {
            	/* on calcule le décalage pour le caractère */
                int decalage = getDecalage(cle, j);
                /* On encode le caractère à l'aide du décalage */
                char cEncode = encoderCaractere(c, decalage);
                /* On l'ajoute à la StringBuilder*/
                sb.append(cEncode);
                j = (j + 1) % cle.length();
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Méthode qui permet de déchiffrer un message.
     * @param cle la clé de déchiffrement.
     * @param message le message a déchiffrer.
     * @return le message dechiffré.
     */
    public static String decoder(String cle, String message) {
        StringBuilder sb = new StringBuilder();
        int j = 0;
        /* Boucle pour décoder le message*/
        for (int i = 0; i < message.length(); i++) {
        	/* On prends le caractère d'indice i */
            char c = message.charAt(i);
            /* On Vérifie sa validité */
            if (isCaractereValide(c)) {
            	/* On récupère le décalage  et on décode le caractère */
                int decalage = getDecalage(cle, j);
                char cDecode = decoderCaractere(c, decalage);
                /* On ajoute le caractère au message non cryptés*/
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
    	/* Récupération du caractère de la clé à l'index passer en paramètre*/
        char c = cle.charAt(index);
        /* Renvoie de la position dans l'alphabet du caractère */
        return alphabet.indexOf(c);
    }
    /**
     * Vérifie si un caractère est valide, c'est-à-dire qu'il est présent dans
     * l'alphabet de la classe.
     * @param c caractère à vérifier.
     * @return true si le caractère est valide, false sinon.
     */
    private static boolean isCaractereValide(char c) {
    	/* 
    	 * On vérifie que la position du caractère dans l'alphabet est différent de -1
    	 * sinon sa veux dire que le caractère ne fait pas partit de l'alphabet
    	 */
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
    	
    	// Récupération de l'index du caractère a encoder
        int index = alphabet.indexOf(c);
        
        // Calcul de l'index du caractère encoder
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
    	/* Récupération de l'index du caractère à décoder */
        int index = alphabet.indexOf(c);
        
        /* Décodage du caractère*/
        int indexDecode = (index - decalage + (alphabet.length())) % (
                alphabet.length());
        return alphabet.charAt(indexDecode);
    }
    
    /** 
     * Getter de l'attribut alphabet
     * @return alphabet
     */
    public static String getAlphabet() {
        return alphabet;
    }
    
    /** 
     * Permet de générer aléatoirement une clé de cryptage pour Vigenere
     * @return cle la clé créée
     */
    public static String creerCleVigenere() {
        StringBuilder cle = new StringBuilder();
        /* Génération d'une taille aléatoire pour la clé*/
        int taille = (int)(Math.random() * 50) + 20;
        
        /* Ajout d'un caractère aléatoire de l'alphabet dans la clé */
        int index = (int)(Math.random() * (alphabet.length() - 100) + 100);
        cle.append(alphabet.charAt(index));
        
        /* Boucle qui génère la clé aléatoirement*/
        for (int i = 1; i < taille; i++) {
        	/* Ajout d'un caractère aléatoire de l'alphabet dans la clé */
            index = (int)(Math.random() * alphabet.length());
            cle.append(alphabet.charAt(index));
        }
        return cle.toString();
    }
    
    /** 
     * Méthode permettant de coder la clé de l'algorithme de Vigenère
     * afin de l'envoyer via l'échange de Diffie-Hellman
     * @param cle la clé de chiffrement
     * @param a entier choisi
     * @param gb entier reçu
     * @param p entier premier modulo
     * @return cle_codee la clé une fois celle-ci cryptée.
     */
    public static BigInteger coderCle(String cle, int a, BigInteger gb, int p) {
        /*Création d'une StringBuilder pour coder la clé*/
    	StringBuilder cle_codee_str = new StringBuilder();
        int index = 0;
        
        /* 
         * Boucle qui ajoute l'index de chaque 
         * caractère de la clé dans la clé codé
         */
        for (int i = 0; i < cle.length(); i++) {
            index = alphabet.indexOf(cle.charAt(i));
            cle_codee_str.append(String.format("%03d", index));
        }
        /* création de la clé coder(récupère l'entier contenue dans la StringBuilder) */
        BigInteger cle_codee = new BigInteger(cle_codee_str.toString());
        
        /* calcul (gB) à la puissance A modulo P */ 
        BigInteger code = gb.pow(a).mod(new BigInteger(Integer.toString(p)));
        
        /* multiplie la clé par le code calculé avant */
        cle_codee = cle_codee.multiply(code);
        return cle_codee;
    }
    
    /** 
     * Méthode permettant de décoder la clé de l'algorithme de Vigenère
     * reçue avec l'échange de Diffie-Hellman
     * @param cle_codee la clé cryptée 
     * @param ga entier reçu
     * @param b entier choisi
     * @param p entier premier modulo
     * @return cle_decodee
     */
    public static String decoderCle(BigInteger cle_codee, BigInteger ga, int b, int p) {
    	/* Calcul de g puissance A à la puissance B le tout modulo p */
        BigInteger code = ga.pow(b).mod(new BigInteger(Integer.toString(p)));
        OutilFichier.ecrire("code.txt", code.toString());
        String test = code + "\n";
        /* Cle  divisé par le code calculé précédement */
        BigInteger cle_codee_divisee = cle_codee.divide(code);
        test += cle_codee_divisee;
        OutilFichier.ecrire("idk.txt", test);
        
        
        int index = 0;
        /* Récupération de la clé divisé dans une string*/
        String cle_codee_str = cle_codee_divisee.toString();
        /* Création stringBuilder pour contenir la clé décodé*/
        StringBuilder cle_decodee = new StringBuilder();
        int taille_finale = cle_codee_str.length();
        /* Boucle qui décode la clé */
        for (int i = 0; i < taille_finale - 2; i+=3) {
        	/* 
        	 * Récupération de l'indice complet du caractère
        	 * (Indice composer de trois chiffre) 
        	 */
            index = Integer.parseInt("" + cle_codee_str.charAt(i) 
            + cle_codee_str.charAt(i + 1) + cle_codee_str.charAt(i + 2));
            /* On ajoute le caractère de l'indice*/
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
    	/* 
    	 * prends un nombre aléatoire entre 1 et 
    	 * 0 et le multiplie par la racine carré de P
    	 */
        return (int)(Math.random() * Math.sqrt(p));
    }
    
    /** 
     * Méthode permettant de génerer aléatoirement
     * un entier premier p entre 1000 et 500000
     * @return p un entier premier entre 1000 et 500000
     */
    public static int genererP() {
    	/* Génère un entier compris entre 1000 et 500000 */
        int p = ((int)(Math.random() * 500000) + 1000);
        /* Récupère le nombre premier qui arrive aprés le nombre généré avant */
        boolean isPrime = true;
        do {
            p++;
            isPrime = true;
            // vérifie que P est premier
            if (p == 1 || p == 0) {
                isPrime = false;
            } else if (p != 2 && p % 2 == 0) {
                isPrime = false;
            } else if (p != 5 && p % 2 == 0) {
                isPrime = false;
            } else {
                for (int i = 2; i <= Math.sqrt(p) && isPrime; i++) {
                    if (p % i == 0) {
                        isPrime = false;
                    }
                }
            }
           
        } while (!isPrime);
        return p;
    }
    
    /**
     * Méthode permettant de génerer aléatoirement
     * un entier générateur g du modulo p
     * @param p un entier premier servant de modulo
     * @return g un entier générateur aléàtoire de p
     */
    public static int genererG(int p) {
        // Initialisation des variables
        TreeSet<Long> listeResultats = new TreeSet<>(); 
        boolean ajoutOk;
        long exposant;
        int g;
        boolean gTrouve = false;
        do {
            listeResultats.clear();
            ajoutOk = true;
            exposant = 1;
            /* Génération d'un nombre aléatoire */
            g = (int)(Math.random() * (p - 4)) + 2;
            /* 
             * Boucle permettant de calculer g * exposant modulo p
             * pour tout j entre 1 et p
             */
            for (int j = 1 ; j < p && ajoutOk ; j++) {
                exposant = (exposant * g) % p;
                ajoutOk = listeResultats.add(exposant);
            }
            
            /* 
             * On vérifie que la liste contiennent bien 
             * tout les entiers compris entre 1 et p et est égales à 1-p 
             */
            if (listeResultats.size() == (p - 1)) {
                gTrouve = true;
            }
        } while (!gTrouve);
        return g;
    }
}