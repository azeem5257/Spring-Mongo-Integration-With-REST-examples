package com.bigbazaar.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bigbazaar.backend.domain.SellerInfo;
import com.bigbazaar.backend.services.SellerInfoService;

@RestController
@CrossOrigin
public class SellerController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final SellerInfoService sellerInfoService;
	
	@Autowired
	SellerController(SellerInfoService sellerInfoService) {
	        this.sellerInfoService = sellerInfoService;
	    }
	
	@RequestMapping(value="/createOrganization", method=RequestMethod.POST)
	public ResponseEntity<?> insertNewOrganization(@RequestBody SellerInfo sellerInfo){
		ResponseEntity<?> responseEntity;
		try{
			responseEntity = sellerInfoService.create(sellerInfo);
			logger.debug("Organization Created:", sellerInfo);
			return responseEntity;
		}catch(Exception ex){
			logger.error("Exception:", ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/getAllOrganizations", method=RequestMethod.GET)
	public ResponseEntity<?> getAllOrganization(){
		ResponseEntity<?> responseEntity;
		try{
			responseEntity = sellerInfoService.findAll();
			logger.debug("List of Organization:", responseEntity.getBody().toString());
			return responseEntity;
		}catch(Exception ex){
			logger.error("Exception:", ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/getOrganizationsByLocation", method=RequestMethod.GET)
	public ResponseEntity<?> getAllOrganizationByLocation(@RequestParam(required=true) Double latitude,
			@RequestParam(required=true) Double longitude){
		ResponseEntity<?> responseEntity;
		try{
			responseEntity = sellerInfoService.findAllByLocation(latitude, longitude, 100);
			logger.debug("List of Organization By Location:", responseEntity.getBody().toString());
			return responseEntity;
		}catch(Exception ex){
			logger.error("Exception:", ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/updateLikeCount", method=RequestMethod.POST)
	public ResponseEntity<?> updateLikesCount(@RequestParam(required=true) String organizationId,
			@RequestParam(required=true) Boolean isIncrement){
		ResponseEntity<?> responseEntity;
		try{
			responseEntity = sellerInfoService.updateLikesCount(organizationId, isIncrement);
			logger.debug("List of Organization By Location:", responseEntity.getBody().toString());
			return responseEntity;
		}catch(Exception ex){
			logger.error("Exception:", ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
