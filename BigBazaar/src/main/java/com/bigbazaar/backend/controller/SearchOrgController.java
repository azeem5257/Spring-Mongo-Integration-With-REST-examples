package com.bigbazaar.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bigbazaar.backend.services.SearchService;

@RestController
@CrossOrigin
public class SearchOrgController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final SearchService searchService;
	
	@Autowired
	SearchOrgController(SearchService searchService){
		this.searchService = searchService;
	}
	
	@RequestMapping(value="searchQuery", method=RequestMethod.GET)
	public ResponseEntity<?> findOrganizationsBySearchQuery(@RequestParam(required=true) String query){
		ResponseEntity<?> responseEntity;
		try{
			System.out.println(query);
			responseEntity = searchService.searchByQuery(query);
			logger.debug("List of organization found for query: ", query);
			return responseEntity;
		}catch(Exception ex){
			logger.error("Exception:", ex);
			ex.printStackTrace();
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="searchQueryByLocation", method=RequestMethod.GET)
	public ResponseEntity<?> findOrganizationBySearchQueryAndLocation(@RequestParam(required=true) String query, 
															@RequestParam(required=true) Integer locationLimit){
		ResponseEntity<?> responseEntity;
		try{
			responseEntity = searchService.searchByQueryAndLocation(query, locationLimit);
			logger.debug("List of organization found for query: ", query);
			return responseEntity;
		}catch(Exception ex){
			logger.error("Exception:", ex);
			ex.printStackTrace();
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="searchOrganizationByLiteral", method=RequestMethod.GET)
	public ResponseEntity<?> findOrganizationByLiteral(@RequestParam(required=true) String query){
		ResponseEntity<?> responseEntity;
		try{
			responseEntity = searchService.searchOrgByLiteral(query);
			logger.debug("List of organization found for query: ", query);
			return responseEntity;
		}catch(Exception ex){
			logger.error("Exception:", ex);
			ex.printStackTrace();
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
