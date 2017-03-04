package com.bigbazaar.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bigbazaar.backend.services.CategorySearchService;

@RestController
@CrossOrigin
public class CategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	private final CategorySearchService categorySearchService;
	
	@Autowired
	CategoryController(CategorySearchService categorySearchService){
		this.categorySearchService = categorySearchService;
	}
	
	@RequestMapping(value="getCategoriesAndSubCategories", method=RequestMethod.GET)
	public ResponseEntity<?> findCategoriesAndSubCategories(){
		ResponseEntity<?> responseEntity;
		try{
			responseEntity = categorySearchService.findAllCategoriesAndSubCategories();
			logger.debug("List of orjsdkjsdkl fjskldjlganization found for query: ", responseEntity.getBody());
			return responseEntity;
		}catch(Exception ex){
			logger.error("Exception:", ex);
			ex.printStackTrace();
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
