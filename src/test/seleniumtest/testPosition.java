package test.seleniumtest;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TestPosition {
	// Adriano Morini
	@Test
	void test() {
		TestPosit p = new TestPosit();
		String s = p.positionT("a", "a");
		assertEquals("Your current position", s);
	}

}
