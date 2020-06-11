package test.junittest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import logic.bean.LoginBean;
import logic.bean.NewCocktailBean;
import logic.controller.LoginController;
import logic.controller.NewCocktailController;
import logic.dao.CocktailDao;
import logic.dao.TagDao;
import logic.dao.UserDao;
import logic.model.Tag;

class TestFindTagByID {
	// Francesca Carpineta
	@Test
	void testFindTag() {

		NewCocktailBean cBean = new NewCocktailBean();
		cBean.setName("cocktailNameTest");
		cBean.setImage("image/c24.png");
		cBean.addTagBean("cocktailTagTest1");
		cBean.addTagBean("cocktailTagTest2");
		cBean.addTagBean("cocktailTagTest3");
		cBean.getRecipe().setProcedure("cocktail procedure test");
		cBean.getRecipe().addIngredientBean("cocktailIngredientTest", Float.parseFloat("2"), 0);
		
		LoginController lc = LoginController.getInstance();
		LoginBean lBean = new LoginBean();
		String usn = "cocktailUsernameTagTest";
		Integer count = 0;
		while(UserDao.findExistingUsername(usn+count.toString())) {
			count++;
		}
		String usern = usn+count.toString();
		lBean.setUsername(usern);
		lc.setBean(lBean);
		
		NewCocktailController cc = new NewCocktailController();
		cc.newCocktailObject(cBean);
		
		List<Integer> list = CocktailDao.findIDByUsername(usern);
		
		List<Tag> t = TagDao.findTagByID(list.get(0));
		if(t.get(0).getTag().equals("cocktailTagTest1") &&
		   t.get(1).getTag().equals("cocktailTagTest2") &&
		   t.get(2).getTag().equals("cocktailTagTest3")) {
			CocktailDao.removeCocktailByID(list.get(0));
			assertTrue(true);
		}
		else {
			CocktailDao.removeCocktailByID(list.get(0));
			assertTrue(false);
		}
		
	}

}
