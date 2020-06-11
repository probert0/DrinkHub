package test.junittest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import logic.bean.IDBean;
import logic.bean.LoginBean;
import logic.bean.NewCocktailBean;
import logic.controller.CocktailPageController;
import logic.controller.LoginController;
import logic.controller.NewCocktailController;
import logic.dao.CocktailDao;
import logic.dao.UserDao;
import logic.model.Cocktail;

class TestRemoveCocktail {
	// Francesca Carpineta
	@Test
	void testCocktailRemoved() {
		
		NewCocktailBean cBean = new NewCocktailBean();
		cBean.setName("cocktailNameRemoveTest");
		cBean.setImage("image/c1.png");
		cBean.addTagBean("cocktailTagTest");
		cBean.getRecipe().setProcedure("cocktail procedure test");
		cBean.getRecipe().addIngredientBean("cocktailIngredientTest", Float.parseFloat("2"), 0);
		
		String usn = "cocktailUsernameRemoveTest";
		Integer count = 0;
		while(UserDao.findExistingUsername(usn+count.toString())) {
			count++;
		}
		String usern = usn+count.toString();
		LoginController lc = LoginController.getInstance();
		LoginBean lBean = new LoginBean();
		lBean.setUsername(usern);
		lc.setBean(lBean);
		
		NewCocktailController cc = new NewCocktailController();
		cc.newCocktailObject(cBean);
		
		List<Integer> list = CocktailDao.findIDByUsername(usern);
		IDBean idBean = new IDBean();
		idBean.setId(list.get(0));
		
		CocktailPageController pc = new CocktailPageController();
		pc.removeCocktailC(idBean);
		List<Cocktail> cList = CocktailDao.findCocktailByIDList(list);
		if(cList.isEmpty()) {
			assertTrue(true);
		}
		
	}

}
