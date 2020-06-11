package test.junittest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logic.bean.CheckIngredientBean;
import logic.controller.NewCocktailController;
import logic.exception.StringIsEmptyException;

class TestCheckIngredient {
	// Francesca Carpineta
	@Test
	void testInsertedIngredientIsValid() {
		NewCocktailController c = new NewCocktailController();
		CheckIngredientBean bean = new CheckIngredientBean("Ingrediente", "33");
		try {
			c.insertedIngredientIsValid(bean);
			assertTrue(true);
		} catch(NumberFormatException | StringIsEmptyException e) {
			assertTrue(false);
		}
	}

}
