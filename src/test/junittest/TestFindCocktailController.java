package test.junittest;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import logic.bean.CocktailBean;
import logic.bean.NewCocktailBean;
import logic.controller.FindCocktailController;
import logic.controller.NewCocktailController;
import logic.model.Cocktail;

class TestFindCocktailController {
	// Adriano Morini
	@Test
	void findCocktailWithNameTest() {
		CocktailBean cocktailBean = new CocktailBean();
		NewCocktailController newCocktailController = new NewCocktailController();
		FindCocktailController findCocktailController = new FindCocktailController();		
		NewCocktailBean newCocktailBean = new NewCocktailBean();
		String cocktailName = "Mojito";
		newCocktailBean.setName(cocktailName);
		newCocktailBean.setImage("image/c17.png");
		newCocktailBean.setUser("AM");
		newCocktailController.newCocktailObject(newCocktailBean);
		cocktailBean.setName(cocktailName);
		List<Cocktail> cocktailsList= findCocktailController.findCocktailWithName(cocktailBean);
		assertEquals(cocktailsList.get(0).getName(), cocktailName);
		
		
		
		
	}

}
