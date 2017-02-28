package com.bigbazaar.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class CategoryOrgRefModel {

	@DBRef
	private SellerInfoModel organization;

	public SellerInfoModel getOrganization() {
		return organization;
	}

	public void setOrganization(SellerInfoModel organization) {
		this.organization = organization;
	}
}
