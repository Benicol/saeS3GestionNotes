/*
 * OutilReseau.java                                                   31/10/2023
 * IUT de Rodez, pas de droit d'auteur.
 */

package modele;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Classe outil permettant :
 *     - récupérer l'adresse IP de la machine sur laquelle on se trouve
 *     - importer des données depuis un autre ordinateur
 *     - exporter des données vers un autre ordinateur
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class OutilReseau {
    
    /**
     * Méthode qui permet de récuperer l'adresse IP de la machine.
     * @return l'adresse IP de la machine.
     */
    public static String getIp() {
        String ip;
        ip ="";
        try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
		
    }
}
