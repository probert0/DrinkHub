package test.junittest;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import logic.bean.BarUserBean;
import logic.bean.FindUserBean;
import logic.bean.SignUpBean;
import logic.controller.FindBarUserController;
import logic.dao.UserDao;
import logic.exception.StringIsEmptyException;
import logic.model.Address;

class TestFindBarUserController {
	// Adriano Morini
	@Test
	void isInsertedBarUsernameValidTest() throws StringIsEmptyException {
		SignUpBean signUpBean = new SignUpBean();
		FindBarUserController findBarUserController = FindBarUserController.getInstance();
		BarUserBean barUserBean = new BarUserBean();
		Address address = new Address(0.0, 0.0, "Viale Appio Claudio, 355, Rome");
		signUpBean.setUsernameSign("AM");
		signUpBean.setNameSign("Adriana");
		signUpBean.setSurnameSign("Morini");
		signUpBean.setImageSign("image/woman");
		signUpBean.setIsBarSign(1);
		signUpBean.setPasswordSign("adm");
		signUpBean.setAddressSign(address);
		String a = signUpBean.getUsernameSign();
		barUserBean.setBarUsername(a);
		if (!findBarUserController.isInsertedBarUsernameValid(barUserBean)) {
			UserDao.insertNewUser(signUpBean);
			
		}
		Boolean t = findBarUserController.isInsertedBarUsernameValid(barUserBean);
		assertTrue(t);
	}
	
	@Test
	void isInsertedUserUsernameValidTest() throws StringIsEmptyException {
		FindBarUserController findBarUserController = FindBarUserController.getInstance();
		FindUserBean findUserBean = new FindUserBean();
		String usn = "AM";
	    Integer count = 0;
	    findUserBean.setUsername(usn);
	    while(findBarUserController.isInsertedUserUsernameValid(findUserBean)) {
	      count++;
	      findUserBean.setUsername(usn + count.toString());
	    }
		Boolean f = findBarUserController.isInsertedUserUsernameValid(findUserBean);
		assertFalse(f);
		}

}
