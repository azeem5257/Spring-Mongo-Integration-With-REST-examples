package com.bigbazaar.backend.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

@Document(collection="categories")
public class CategoryInfoModel {

	@Id
	private String Id;
	@TextIndexed(weight=2)
	private String name;
	@TextIndexed(weight=1)
	private List<String> sub_categories;
	private List<CategoryOrgRefModel> organization__id;
	@TextScore
	private Float score;
	
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public List<String> getSub_categories() {
		return sub_categories;
	}

	public void setSub_categories(List<String> sub_categories) {
		this.sub_categories = sub_categories;
	}

	public List<CategoryOrgRefModel> getOrganization__id() {
		return organization__id;
	}

	public void setOrganization__id(List<CategoryOrgRefModel> organization__id) {
		this.organization__id = organization__id;
	}
}
