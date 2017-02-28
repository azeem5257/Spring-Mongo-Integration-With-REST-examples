package com.bigbazaar.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bigbazaar.backend.domain.ProductInfo;
import com.bigbazaar.backend.services.ProductService;

@RestController
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final ProductService productService;
	
	@Autowired
	ProductController(ProductService productService) {
	        this.productService = productService;
	}

	@RequestMapping(value="/insertProduct", method=RequestMethod.POST)
	public ResponseEntity<?> insertNewProduct(@RequestBody ProductInfo productInfo){
		ResponseEntity<?> responseEntity;
		try{
			responseEntity = productService.create(productInfo);
			logger.debug("New Product Inserted:", productInfo);
			return responseEntity;
		}catch(Exception ex){
			logger.error("Exception:", ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/findProduct", method=RequestMethod.GET)
	public ResponseEntity<?> findProductById(){
		ResponseEntity<?> responseEntity;
		try{
			responseEntity = productService.findById("585839a9bd44102ceccd5434");
			logger.debug("New Product Inserted:", "585839a9bd44102ceccd5434");
			return responseEntity;
		}catch(Exception ex){
			logger.error("Exception:", ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
