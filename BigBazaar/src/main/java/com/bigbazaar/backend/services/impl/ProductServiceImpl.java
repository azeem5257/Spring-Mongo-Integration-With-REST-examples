package com.bigbazaar.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bigbazaar.backend.domain.ProductInfo;
import com.bigbazaar.backend.domain.ProductInfoResponse;
import com.bigbazaar.backend.model.CategoryInfoModel;
import com.bigbazaar.backend.model.CategoryOrgRefModel;
import com.bigbazaar.backend.model.ProductInfoModel;
import com.bigbazaar.backend.model.ProductInfoModel.OrganizationIdDbRef;
import com.bigbazaar.backend.model.ProductInfoResponseModel;
import com.bigbazaar.backend.model.SellerInfoModel;
import com.bigbazaar.backend.repos.CategoriesRepository;
import com.bigbazaar.backend.repos.ProductRepository;
import com.bigbazaar.backend.services.ProductService;


@Service
public class ProductServiceImpl implements ProductService{

	private static final Logger logger = LoggerFactory.getLogger(SellerInfoServiceImpl.class);
	private final ProductRepository productRepository;
	private final CategoriesRepository categoriesRepository;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	ProductServiceImpl(ProductRepository productRepository, CategoriesRepository categoriesRepository){
		this.productRepository = productRepository;
		this.categoriesRepository = categoriesRepository;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<ProductInfo> create(ProductInfo productInfoS) {
		ProductInfoModel productInfoModel = new ProductInfoModel();
		CategoryInfoModel categoryInfoModel = new CategoryInfoModel();
		SellerInfoModel sellerInfoModel = new SellerInfoModel();
		ProductInfo productInfo = new ProductInfo();
		ResponseEntity<ProductInfo> responseEntity = null;
		
		try{
			productInfoModel.setName(productInfoS.getName());
			productInfoModel.setThumbnail(productInfoS.getThumbnail());
			productInfoModel.setImages(productInfoS.getImages());
			
			/* Save category in prodIuct document for reference*/
			String category = productInfoS.getCategory();
			String subCategory = productInfoS.getSubCategory();
			
			if((category != null && !category.isEmpty()) &&
				(subCategory != null && !subCategory.isEmpty())){
				category += "%" + subCategory;
			}			
			productInfoModel.setCategory(category);
			
			//set the organization reference in the product
			OrganizationIdDbRef organizationRef = productInfoModel.new OrganizationIdDbRef();
			organizationRef.setId(productInfoS.getOrganization__id());			
			productInfoModel.setOrganization(organizationRef);
			
			//Save the product in the database
			productInfoModel = productRepository.save(productInfoModel);
			
			/*Save the corresponding category in the Category document. First check if this product is in new
			 *category or sub-category. If not then update the corresponding category with current organization */
			if(productInfoS.getIsNewCategory()){
				categoryInfoModel.setName(productInfoS.getCategory());
				
				//Check if there is any sub-category. If yes, then save it.
				if(productInfoS.getIsNewSubCategory() && !productInfoS.getSubCategory().isEmpty()){
					List<String> lstSub_category = new ArrayList<String>();
					lstSub_category.add(productInfoS.getSubCategory());
					categoryInfoModel.setSub_categories(lstSub_category);
				}
				
				sellerInfoModel.setId(productInfoS.getOrganization__id());
				
				CategoryOrgRefModel categoryOrgRefModel = new CategoryOrgRefModel();
				categoryOrgRefModel.setOrganization(sellerInfoModel);
				
				List<CategoryOrgRefModel> lstSellerInfoModel = new ArrayList<CategoryOrgRefModel>();
				lstSellerInfoModel.add(categoryOrgRefModel);
				categoryInfoModel.setOrganization__id(lstSellerInfoModel);
				
				categoriesRepository.save(categoryInfoModel);
			}else if(!productInfoS.getIsNewCategory() && productInfoS.getIsNewSubCategory()){
				//Get existing category and update sub-category in the list
				Query query = new Query(Criteria.where("name").is(productInfoS.getCategory()));
				Update update = new Update();
				update.push("sub_categories", productInfoS.getSubCategory());
				
				sellerInfoModel.setId(productInfoS.getOrganization__id());
				List<SellerInfoModel> lstSellerInfoModel = new ArrayList<SellerInfoModel>();
				lstSellerInfoModel.add(sellerInfoModel);
				
				CategoryOrgRefModel categoryOrgRefModel = new CategoryOrgRefModel();
				categoryOrgRefModel.setOrganization(sellerInfoModel);
				
				update.addToSet("organization__id", categoryOrgRefModel);
				
				mongoTemplate.findAndModify(query, update, CategoryInfoModel.class);
			}else{
				//Update organization Id in this category only
				Query query = new Query(Criteria.where("name").is(productInfoS.getCategory()));
				Update update = new Update();
				
				CategoryOrgRefModel categoryOrgRefModel = new CategoryOrgRefModel();
				categoryOrgRefModel.setOrganization(sellerInfoModel);
				
				update.addToSet("organization__id", sellerInfoModel);
				mongoTemplate.findAndModify(query, update, CategoryInfoModel.class);
			}
			
			productInfo.setName(productInfoModel.getName());
			
			logger.debug("New Product Inserted:", productInfoS);
			responseEntity = new ResponseEntity<ProductInfo>(productInfo, HttpStatus.CREATED);
		}catch(Exception ex){
			ex.printStackTrace();
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;//new ResponseEntity<ProductInfo>(productInfo, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<ProductInfoResponse> findById(String id) {
		//Check for existing data before insert
		Query query = new Query(Criteria.where("_id").is(id));
		
		ProductInfoResponseModel productInfoModel = mongoTemplate.findOne(query, ProductInfoResponseModel.class);
		
		if(productInfoModel == null){
			return null;
		}else{
			ProductInfoResponse productInfoResponse = new ProductInfoResponse();
			productInfoResponse.setCategories(productInfoModel.getCategories());
			productInfoResponse.setImages(productInfoModel.getImages());
			productInfoResponse.setName(productInfoModel.getName());
			productInfoResponse.setOrganization__id(productInfoModel.getOrganization__id().getId());
			
			return new ResponseEntity<ProductInfoResponse>(productInfoResponse, HttpStatus.OK);
		}
	}
}
