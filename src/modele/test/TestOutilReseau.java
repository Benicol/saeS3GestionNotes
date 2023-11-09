package modele.test;

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
		assertTrue(OutilReseau.getIp()
		        .matches("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$"));
	}

}
