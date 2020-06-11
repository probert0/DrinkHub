package logic.bean;

import java.util.ArrayList;
import java.util.List;

import logic.exception.PostListIsNullException;
import logic.model.Cocktail;

public class CocktailPostBean {
	private List<Cocktail> list;
	private String username;
	
	public CocktailPostBean() {
		this.list = null;
	}
	
	public List<Cocktail> getPostList() throws PostListIsNullException {
		return list;
	}

	public void setPostList(List<Cocktail> l) {
		this.list = (ArrayList<Cocktail>) l;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
