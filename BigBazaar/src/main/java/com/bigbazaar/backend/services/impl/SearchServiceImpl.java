package com.bigbazaar.backend.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;

import com.bigbazaar.backend.domain.SearchOrganizationResult;
import com.bigbazaar.backend.helper.Constants;
import com.bigbazaar.backend.model.CategoryInfoModel;
import com.bigbazaar.backend.model.CategoryOrgRefModel;
import com.bigbazaar.backend.model.SellerInfoModel;
import com.bigbazaar.backend.services.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	private static final Logger logger = LoggerFactory.getLogger(SellerInfoServiceImpl.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private SearchOrganizationResult createSearchOrgObject(SellerInfoModel organization){
		SearchOrganizationResult searchOrganizationResult = new SearchOrganizationResult();
		
		searchOrganizationResult.setOrgId(organization.getId());
		searchOrganizationResult.setOrgName(organization.getOrgName());
		searchOrganizationResult.setThumbnail(organization.getThumbnail());
		//searchOrganizationResult.setLocation(organization.getLocation());
		searchOrganizationResult.setImages(organization.getImages());
		
		return searchOrganizationResult;
	}
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<Set<SearchOrganizationResult>> searchByQuery(String query) {
		ResponseEntity<Set<SearchOrganizationResult>> responseEntity = null;
		Set<SearchOrganizationResult> lstSearchResultOrg = new HashSet<SearchOrganizationResult>();
		
		try{
			logger.debug("Start searching organizations for query: ", query);
			
			TextCriteria textCriteria = new TextCriteria();
			textCriteria.matching(query);
			Query mongoQuery = TextQuery.queryText(textCriteria)
										.sortByScore()
										.with(new PageRequest(0, 10));
			mongoQuery.fields().include("organization__id");
			mongoQuery.fields().include("name");
			
			List<CategoryInfoModel> lstCategoryInfoModel = mongoTemplate.find(mongoQuery, CategoryInfoModel.class);
			
			for(CategoryInfoModel categoryInfoModel : lstCategoryInfoModel){
				for(CategoryOrgRefModel categoryOrgRefModel : categoryInfoModel.getOrganization__id()){
					if(categoryOrgRefModel.getOrganization() != null){
						SearchOrganizationResult searchOrganizationResult = createSearchOrgObject(categoryOrgRefModel.getOrganization());
						lstSearchResultOrg.add(searchOrganizationResult);
					}
				}
			}
			responseEntity = new ResponseEntity<Set<SearchOrganizationResult>>(lstSearchResultOrg, HttpStatus.OK);
			
			return responseEntity;
			
		}catch(Exception ex){
			ex.printStackTrace();
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<Set<SearchOrganizationResult>> searchByQueryAndLocation(String query, Integer locationLimit) {
		ResponseEntity<Set<SearchOrganizationResult>> responseEntity = null;
		Set<SearchOrganizationResult> lstSearchResultOrg = new HashSet<SearchOrganizationResult>();
		
		try{
			logger.debug("Start searching organizations for query: ", query);
			
			TextCriteria textCriteria = new TextCriteria();
			textCriteria.matching(query);			
		
			Query mongoQuery = TextQuery.queryText(textCriteria)
										.sortByScore()
										.addCriteria(Criteria.where("location").
													 within(new Circle(new Point(13, 13), locationLimit)))
										.with(new PageRequest(0, 10));
			mongoQuery.fields().include("organization__id");
			
			List<CategoryInfoModel> lstCategoryInfoModel = mongoTemplate.find(mongoQuery, CategoryInfoModel.class);
			
			for(CategoryInfoModel categoryInfoModel : lstCategoryInfoModel){
				for(CategoryOrgRefModel categoryOrgRefModel : categoryInfoModel.getOrganization__id()){
					SearchOrganizationResult searchOrganizationResult = createSearchOrgObject(categoryOrgRefModel.getOrganization());
					lstSearchResultOrg.add(searchOrganizationResult);
				}
			}
			responseEntity = new ResponseEntity<Set<SearchOrganizationResult>>(lstSearchResultOrg, HttpStatus.OK);
			
			return responseEntity;
			
		}catch(Exception ex){
			ex.printStackTrace();
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<Set<SearchOrganizationResult>> searchByCategory(String categoryName) {
		ResponseEntity<Set<SearchOrganizationResult>> responseEntity = null;
		Set<SearchOrganizationResult> lstSearchResultOrg = new HashSet<SearchOrganizationResult>();
		
		try{
			logger.debug("Start searching organizations for category name: ", categoryName);
			
			Query query = new Query(Criteria.where(Constants.categoryName_Text).is(categoryName));
			query.fields().include("organization__id");
			
			CategoryInfoModel categoryInfoModel = mongoTemplate.findOne(query, CategoryInfoModel.class);
			
			for(CategoryOrgRefModel categoryOrgRefModel : categoryInfoModel.getOrganization__id()){
				SearchOrganizationResult searchOrganizationResult = createSearchOrgObject(categoryOrgRefModel.getOrganization());
				lstSearchResultOrg.add(searchOrganizationResult);
			}
			
			responseEntity = new ResponseEntity<Set<SearchOrganizationResult>>(lstSearchResultOrg, HttpStatus.OK);
			
			return responseEntity;
		}catch(Exception ex){
			ex.printStackTrace();
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<Set<SearchOrganizationResult>> searchOrgByLiteral(String query) {
		ResponseEntity<Set<SearchOrganizationResult>> responseEntity = null;
		Set<SearchOrganizationResult> lstSearchResultOrg = new HashSet<SearchOrganizationResult>();
		
		try{
			logger.debug("Start searching organizations for String Literal: ", query);
			
			TextCriteria textCriteria = new TextCriteria();
			textCriteria.matching(query);			
		
			Query mongoQuery = TextQuery.queryText(textCriteria)
										.sortByScore()
										.with(new PageRequest(0, 10));
			
			List<SellerInfoModel> lstCategoryInfoModel = mongoTemplate.find(mongoQuery, SellerInfoModel.class);
			

			for(SellerInfoModel sellerInfoModel : lstCategoryInfoModel){
				SearchOrganizationResult searchOrganizationResult = createSearchOrgObject(sellerInfoModel);
				lstSearchResultOrg.add(searchOrganizationResult);
			}
		
			responseEntity = new ResponseEntity<Set<SearchOrganizationResult>>(lstSearchResultOrg, HttpStatus.OK);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
}
