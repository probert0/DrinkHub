package test.junittest;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import logic.bean.SignUpBean;
import logic.controller.SignUpController;
import logic.model.Address;

class TestSignUpController {
	//@Roberto Prete
	@Test
	void testFindUsername() {
		SignUpController c = SignUpController.getInstance();
		SignUpBean bean = new SignUpBean();
		bean.setUsernameSign("fraca");
		bean.setNameSign("Francesca");
		bean.setSurnameSign("Carpineta");
		bean.setImageSign("image/woman.png");
		bean.setIsBarSign(1);
		Address a = new Address(41.8383438,12.888712,"Palestrina");
		bean.setAddressSign(a);
		c.setBean(bean);
		boolean r = c.findUsername();
		assertTrue(r);
	
	}

}
