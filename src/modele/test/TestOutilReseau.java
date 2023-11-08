package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import modele.OutilReseau;


/**
 * Tests en JUnit de la classe "OutilReseau".
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
class TestOutilReseau {
    
    /** 
     * Test de la methode getIP() de la classe outilReseau
     */
	@Test
	void testGetIp() {
	    
	    
		assertDoesNotThrow(() -> OutilReseau.getIp());
		assertEquals("192.168.1.18",OutilReseau.getIp());
		assertNotEquals("122.100.1.25",OutilReseau.getIp());
	}

}
