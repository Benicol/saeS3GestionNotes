package modele;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestOutilReseau {

	@Test
	void test() {
		assertDoesNotThrow(() -> OutilReseau.getIp());
		assertEquals("192.168.1.18",OutilReseau.getIp());
		assertNotEquals("122.100.1.25",OutilReseau.getIp());
		
	}

}
