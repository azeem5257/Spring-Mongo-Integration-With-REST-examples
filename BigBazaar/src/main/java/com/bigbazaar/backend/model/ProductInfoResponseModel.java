package com.bigbazaar.backend.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="product")
public class ProductInfoResponseModel {

	@Id
	private String Id;
	private String name;
	private String thumbnail;
	private List<String> images;
	private List<String> categories;
	
	@DBRef
	private SellerInfoModel organization__id;
	
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

	public SellerInfoModel getOrganization__id() {
		return organization__id;
	}

	public void setOrganization__id(SellerInfoModel organization__id) {
		this.organization__id = organization__id;
	}
}
