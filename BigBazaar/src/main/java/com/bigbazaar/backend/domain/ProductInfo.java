package com.bigbazaar.backend.domain;

import java.util.List;

public class ProductInfo {
	
	private String name;
	private String thumbnail;
	private List<String> images;
	private String category;
	private String subCategory;
	private Boolean isNewCategory;
	private Boolean isNewSubCategory;
	private String organization__id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public Boolean getIsNewCategory() {
		return isNewCategory;
	}
	public void setIsNewCategory(Boolean isNewCategory) {
		this.isNewCategory = isNewCategory;
	}
	public Boolean getIsNewSubCategory() {
		return isNewSubCategory;
	}
	public void setIsNewSubCategory(Boolean isNewSubCategory) {
		this.isNewSubCategory = isNewSubCategory;
	}
	public String getOrganization__id() {
		return organization__id;
	}
	public void setOrganization__id(String organization__id) {
		this.organization__id = organization__id;
	}
}
