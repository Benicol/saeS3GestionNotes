/**
 * OutilReseau.java                                                                                 21/10/2015
 * No copyright.
 */

package modele;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class OutilReseau {
    /**
     * Methode qui permet de recuperer l'adresse IP de la machine.
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
