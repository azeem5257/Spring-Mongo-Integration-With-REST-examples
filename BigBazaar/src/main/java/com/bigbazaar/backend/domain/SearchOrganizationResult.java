package com.bigbazaar.backend.domain;

import java.util.List;
import java.util.Set;

import com.bigbazaar.backend.helper.LocationValues;

public class SearchOrganizationResult {

	private String OrgId;
	private String orgName;
	private Set<LocationValues> location;
	private String thumbnail;
	private List<String> images;
	
	public String getOrgId() {
		return OrgId;
	}
	public void setOrgId(String orgId) {
		OrgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Set<LocationValues> getLocation() {
		return location;
	}
	public void setLocation(Set<LocationValues> location) {
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
	
	 @Override
	 public boolean  equals (Object object) {
		 SearchOrganizationResult searchOrganizationResult = (SearchOrganizationResult)object;
		 if(OrgId.equals(searchOrganizationResult.getOrgId())){
			 return true;
		 }else{
			 return false;
		 }
	 }
	 
	 @Override
	 public int hashCode(){
		 return this.OrgId.hashCode();
	 }
}
