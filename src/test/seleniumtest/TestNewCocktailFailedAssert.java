package test.seleniumtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestNewCocktailFailedAssert {
	// Francesca Carpineta
	@Test
	void test() {
		TestNewCocktailFailed t = new TestNewCocktailFailed();
		String s = t.newCocktailFailedT("fra", "rum");
		assertEquals("ERROR: Input not valid.", s);
	}

}
