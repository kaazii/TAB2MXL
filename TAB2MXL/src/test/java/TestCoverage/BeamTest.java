package TestCoverage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import TAB2MXL.Beam;

class BeamTest {
	public Beam beam;
	@BeforeEach
	void setUp() throws Exception {
		beam = new Beam(1,"State");
	}

	@Test
	void testConstructor() {
		assertEquals(1,beam.number);
		assertEquals("State", beam.state);
	}

}
