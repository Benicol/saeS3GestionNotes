/*
 * Enseignement.java                                                  2 nov 2023                                                                                21/10/2015
 * IUT de Rodez, pas de copyright
 */

package modele;

/** 
 * Interface Enseignement permet de gérer le nom de tous les enseignements 
 * différents (ressources, compétences, SAÉ). Le format attendu pour ces classes
 * étant : X1.01 - Nom de l'enseignement
 * Cela correspond à : 
 *     - l'identifiant : un code qui désigne l'enseignement par une lettre
 *       (R pour ressource, S pour SAÉ, ...) et deux chiffres (le premier 
 *       correspond au numéro du semestre en cours)
 *     - le libellé : un nom explicite qui désigne l'enseignement
 * Le tout, une fois concaténé, forme l'intitulé de l'enseignement.
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public interface Enseignement {
    
    /**
     * Getter du libelle d'un enseignement
     * @return le libelle de l'enseignement 
     */
    String getLibelle();
    
    /**
     * Getter de l'identifiant d'un enseignement
     * @return l'identifiant de l'enseignement 
     */
    String getIdentifiant();
    
    /**
     * Méthode qui concatène l'identifiant et le libellé afin de créer 
     * l'intitulé de l'enseignement. Le format attendu est le suivant : 
     * X01.1 - Nom de l'enseignement
     * @return l'intitulé de l'enseignement
     */
    String creerIntitule();
}

