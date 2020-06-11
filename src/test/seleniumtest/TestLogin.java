package test.seleniumtest;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class TestLogin {
	// @Roberto Prete
	@Test
	void test() {
		SeleniumTestLogin t = new SeleniumTestLogin();
		String output = t.logTest("robpr","password");
		assertEquals("Roberto Prete", output, 0);
	}

}
