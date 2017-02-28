package com.bigbazaar.backend.services;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bigbazaar.backend.domain.SearchOrganizationResult;
import com.bigbazaar.backend.domain.SellerInfo;

public interface SellerInfoService {
	
	ResponseEntity<SellerInfo> create(SellerInfo user);
	ResponseEntity<SellerInfo> findByUserName(String userName);
	ResponseEntity<List<SearchOrganizationResult>> findByOrgName(String orgName);
	ResponseEntity<List<SellerInfo>> findAll();	
	ResponseEntity<List<SellerInfo>> findAllByLocation(Double latitude, Double longitude, Integer distance);
	ResponseEntity<Integer> updateLikesCount(String orgId, Boolean isIncrement);
}
