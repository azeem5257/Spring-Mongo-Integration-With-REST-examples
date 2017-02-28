package com.bigbazaar.backend.domain;

import java.util.List;

import com.bigbazaar.backend.helper.LocationValues;



public class SellerInfo {
	
	private String Id;
	private String orgName;
	private String userName;
	private String password;
	private LocationValues location;
	private String thumbnail;
	private List<String> images;
	private String description;
	private Double distanceFromCurrentLoc;
	private Integer numOfLikes;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public LocationValues getLocation() {
		return location;
	}
	public void setLocation(LocationValues location) {
		this.location = location;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getDistanceFromCurrentLoc() {
		return distanceFromCurrentLoc;
	}
	public void setDistanceFromCurrentLoc(Double distanceFromCurrentLoc) {
		this.distanceFromCurrentLoc = distanceFromCurrentLoc;
	}
	public Integer getNumOfLikes() {
		return numOfLikes;
	}
	public void setNumOfLikes(Integer numOfLikes) {
		this.numOfLikes = numOfLikes;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
}

