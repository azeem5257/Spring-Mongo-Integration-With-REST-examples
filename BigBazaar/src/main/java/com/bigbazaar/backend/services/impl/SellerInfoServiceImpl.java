package com.bigbazaar.backend.services.impl;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bigbazaar.backend.domain.SearchOrganizationResult;
import com.bigbazaar.backend.domain.SellerInfo;
import com.bigbazaar.backend.helper.Constants;
import com.bigbazaar.backend.helper.LocationValues;
import com.bigbazaar.backend.model.SellerInfoModel;
import com.bigbazaar.backend.repos.SellerInfoRepository;
import com.bigbazaar.backend.services.SellerInfoService;

@Service
final public class SellerInfoServiceImpl implements SellerInfoService{
	
	private static final Logger logger = LoggerFactory.getLogger(SellerInfoServiceImpl.class);
	private final SellerInfoRepository sellerInfoRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	SellerInfoServiceImpl(SellerInfoRepository sellerInfoRepository){
		this.sellerInfoRepository = sellerInfoRepository;
	}

	@Override
	public ResponseEntity<SellerInfo> create(SellerInfo sellerInfoS) {
		SellerInfoModel sellerInfoModel = new SellerInfoModel();
		SellerInfo sellerInfo = new SellerInfo();
		ResponseEntity<SellerInfo> responseEntity = null;
		
		try{
			ResponseEntity<SellerInfo> existingUser = findByUserName(sellerInfoS.getUserName());
			
			/*Check if data exist or not*/
			if(existingUser == null){//Organization with same userName does not exist
				logger.debug("Creating new organization:", sellerInfoS);
				
				LocationValues locationValues = sellerInfoS.getLocation();
				Point point = new Point(locationValues.getLatitude(), locationValues.getLongitude());
						
				//Get all data coming from request and save it into model to put it in database
				sellerInfoModel.setOrgName(sellerInfoS.getOrgName());
				sellerInfoModel.setUserName(sellerInfoS.getUserName());
				sellerInfoModel.setPassword(sellerInfoS.getPassword());
				sellerInfoModel.setThumbnail(sellerInfoS.getThumbnail());
				sellerInfoModel.setLocation(new GeoJsonPoint(point));
				sellerInfoModel.setImages(sellerInfoS.getImages());
				sellerInfoModel.setDescription(sellerInfoS.getDescription());
				
				//save data into database
				sellerInfoModel = sellerInfoRepository.save(sellerInfoModel);
				logger.debug("New Organization Created:", sellerInfoModel);
				
				//Create data to send as a response				
				sellerInfo = craeteSellerInfoResponse(sellerInfoModel);
				
				responseEntity = new ResponseEntity<SellerInfo>(sellerInfo, HttpStatus.CREATED);
			}else{//Organization with same userName exist
				logger.debug("Already Existing Organization:", sellerInfoModel);
				
				sellerInfo.setUserName(existingUser.getBody().getUserName());
				responseEntity = new ResponseEntity<SellerInfo>(sellerInfo, HttpStatus.CONFLICT);
			}
		}catch(Exception ex){
			
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<SellerInfo> findByUserName(String userName) {
		//Check for existing data before insert
		Query query = new Query(Criteria.where(Constants.userName__Text).is(userName));
		
		SellerInfoModel sellerInfoModel = mongoTemplate.findOne(query, SellerInfoModel.class);
		
		if(sellerInfoModel == null){
			return null;
		}else{
			SellerInfo sellerInfo = new SellerInfo();
			//set response data to domain class 
			sellerInfo.setOrgName(sellerInfoModel.getOrgName());
			sellerInfo.setUserName(sellerInfoModel.getUserName());
			
			return new ResponseEntity<SellerInfo>(sellerInfo, HttpStatus.OK);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<List<SearchOrganizationResult>> findByOrgName(String orgName) {
		ResponseEntity<List<SearchOrganizationResult>> responseEntity = null;
		List<SearchOrganizationResult> lstSearchResultOrg = new ArrayList<SearchOrganizationResult>();
		
		try{
			logger.debug("Start searching organizations: ", orgName);
			
			TextCriteria textCriteria = new TextCriteria();
			textCriteria.matching(orgName);
			
			Query mongoQuery = TextQuery.queryText(textCriteria)
					.sortByScore()
					.with(new PageRequest(0, 10));
			
			List<SellerInfoModel> lstSellerInfoModel = mongoTemplate.find(mongoQuery, SellerInfoModel.class);
			
			for(SellerInfoModel sellerInfoModel : lstSellerInfoModel){
				SearchOrganizationResult searchOrganizationResult = createSearchOrgObject(sellerInfoModel);
				lstSearchResultOrg.add(searchOrganizationResult);
			}
			
			responseEntity = new ResponseEntity<List<SearchOrganizationResult>>(lstSearchResultOrg, HttpStatus.OK);
			
			return responseEntity;
		}catch(Exception ex){
			ex.printStackTrace();
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private SearchOrganizationResult createSearchOrgObject(SellerInfoModel organization){
		SearchOrganizationResult searchOrganizationResult = new SearchOrganizationResult();
		
		searchOrganizationResult.setOrgId(organization.getId());
		searchOrganizationResult.setOrgName(organization.getOrgName());
		searchOrganizationResult.setThumbnail(organization.getThumbnail());
		//searchOrganizationResult.setLocation(organization.getLocation());
		searchOrganizationResult.setImages(organization.getImages());
		
		return searchOrganizationResult;
	}

	@Override
	public ResponseEntity<List<SellerInfo>> findAll() {
		List<SellerInfoModel> lstSellerInfoModel = new ArrayList<SellerInfoModel>();
		List<SellerInfo> lstSellerInfo = new ArrayList<SellerInfo>();
		ResponseEntity<List<SellerInfo>> responseEntity = null;
		
		lstSellerInfoModel = this.sellerInfoRepository.findAll();
		
		for(SellerInfoModel sellerInfoModel : lstSellerInfoModel){
			SellerInfo sellerInfo = craeteSellerInfoResponse(sellerInfoModel);
			lstSellerInfo.add(sellerInfo);
		}
		
		responseEntity = new ResponseEntity<List<SellerInfo>>(lstSellerInfo, HttpStatus.OK);
		
		return responseEntity;		
	}	

	@Override
	public ResponseEntity<List<SellerInfo>> findAllByLocation(Double latitude, Double longitude, Integer distance) {
		List<SellerInfo> lstSellerInfo = new ArrayList<SellerInfo>();
		ResponseEntity<List<SellerInfo>> responseEntity = null;

		Point location = new Point(latitude, longitude);	
		
		GeoResults<SellerInfoModel> geoResults = this.sellerInfoRepository.findByLocationNear(location, new Distance(distance, Metrics.KILOMETERS));
		
		for(GeoResult<SellerInfoModel> geoResultsSellerInfoModel : geoResults.getContent()){
			SellerInfo sellerInfo = craeteSellerInfoResponse(geoResultsSellerInfoModel.getContent());
			sellerInfo.setDistanceFromCurrentLoc(geoResultsSellerInfoModel.getDistance().getValue());	
			
			lstSellerInfo.add(sellerInfo);
		}
		
		responseEntity = new ResponseEntity<List<SellerInfo>>(lstSellerInfo, HttpStatus.OK);
		
		return responseEntity;
	}
	
	private SellerInfo craeteSellerInfoResponse(SellerInfoModel sellerInfoModel){
		SellerInfo sellerInfo = new SellerInfo();
		
		LocationValues locationValues = new LocationValues(sellerInfoModel.getLocation().getX(), 
														   sellerInfoModel.getLocation().getY());		
		//Create data to send as a response
		sellerInfo.setId(sellerInfoModel.getId());
		sellerInfo.setOrgName(sellerInfoModel.getOrgName());
		sellerInfo.setUserName(sellerInfoModel.getUserName());		
		sellerInfo.setThumbnail(sellerInfoModel.getThumbnail());
		sellerInfo.setImages(sellerInfoModel.getImages());
		sellerInfo.setDescription(sellerInfoModel.getDescription());
		sellerInfo.setNumOfLikes(sellerInfoModel.getNumOfLikes());
		sellerInfo.setLocation(locationValues);
		
		return sellerInfo;
	}

	@Override
	public ResponseEntity<Integer> updateLikesCount(String orgId, Boolean isIncrement){
		ResponseEntity<Integer> responseEntity = null;
		SellerInfoModel sellerInfoModel = null;
		
		Query query = new Query(Criteria.where("_id").is(orgId));
		query.fields().include(Constants.numOfLikes_Text);
		
		Update update = new Update().inc(Constants.numOfLikes_Text, isIncrement ? 1 : -1);
		
		sellerInfoModel = mongoTemplate.findAndModify(query, update, SellerInfoModel.class);
		
		responseEntity = new ResponseEntity<Integer>(sellerInfoModel.getNumOfLikes(), HttpStatus.OK);
		return responseEntity;
	}
}
