/*
 * tmp.java                                      10 Nov 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft" 
 */
package application;

import modele.Modele;

/** TODO comment class responsibility (SRP)
 * @author benji
 *
 */
public class tmp {

    /** TODO comment method role
     * @param args
     */
    public static void main(String[] args) {
        Modele.reset();
        Modele.importer("src\\application\\testModeleParametrage.csv");
        Modele.getUtilisateur().setPseudo("Benjamin Nicol");
        Modele.sauvegarder();

    }

}
