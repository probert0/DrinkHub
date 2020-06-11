package test.junittest;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import logic.dao.UserDao;

class TestUserDao {
	//@Roberto Prete
	@Test
	void testFindExistingUsername() {
		boolean result = UserDao.findExistingUsername("robpr");
		assertTrue(result);
	}

}
