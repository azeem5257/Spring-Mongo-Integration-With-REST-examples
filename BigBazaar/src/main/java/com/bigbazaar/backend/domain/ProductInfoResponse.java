package com.bigbazaar.backend.domain;

import java.util.List;

public class ProductInfoResponse {

	private String name;
	private String thumbnail;
	private List<String> images;
	private List<String> categories;
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
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public String getOrganization__id() {
		return organization__id;
	}
	public void setOrganization__id(String organization__id) {
		this.organization__id = organization__id;
	}
}
