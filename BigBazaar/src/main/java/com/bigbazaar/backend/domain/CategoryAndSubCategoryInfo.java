package com.bigbazaar.backend.domain;

import java.util.ArrayList;
import java.util.List;

public class CategoryAndSubCategoryInfo {
	
	private List<String> categories;
	private List<String> subCategories;
	
	public CategoryAndSubCategoryInfo(){
		categories = new ArrayList<String>();
		subCategories = new ArrayList<String>();
	}
	
	public List<String> getCategories() {
		return categories;
	}
	public void addCategories(String category) {
		this.categories.add(category);
	}
	public List<String> getSubCategories() {
		return subCategories;
	}
	public void addSubCategories(List<String> subCategories) {
		this.subCategories.addAll(subCategories);
	}
}
