package com.bigbazaar.backend.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="product")
public class ProductInfoModel {

	@Id
	private String Id;
	private String name;
	private String thumbnail;
	private List<String> images;
	private String category;
	
	@DBRef
	private OrganizationIdDbRef organization;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
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
	public OrganizationIdDbRef getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationIdDbRef organization__id) {
		this.organization = organization__id;
	}
	
	@Document(collection="sellerInfo")
	public class OrganizationIdDbRef {
		
		@Id
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}
	
}
