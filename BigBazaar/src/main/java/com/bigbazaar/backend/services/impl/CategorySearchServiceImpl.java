package com.bigbazaar.backend.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bigbazaar.backend.domain.CategoryAndSubCategoryInfo;
import com.bigbazaar.backend.model.CategoryInfoModel;
import com.bigbazaar.backend.repos.CategoriesRepository;
import com.bigbazaar.backend.services.CategorySearchService;

@Service
public class CategorySearchServiceImpl implements CategorySearchService{
	
	private final CategoriesRepository categoriesRepository;
	
	@Autowired
	CategorySearchServiceImpl(CategoriesRepository categoriesRepository){
		this.categoriesRepository = categoriesRepository;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<CategoryAndSubCategoryInfo> findAllCategoriesAndSubCategories() {
		ResponseEntity<CategoryAndSubCategoryInfo> responseEntity = null;
		CategoryAndSubCategoryInfo categoryAndSubCategoryInfo = new CategoryAndSubCategoryInfo();
		
		try{
			List<CategoryInfoModel> lstCategoryInfoModel = categoriesRepository.findAll();
			
			for(CategoryInfoModel categoryInfoModel : lstCategoryInfoModel){
				categoryAndSubCategoryInfo.addCategories(categoryInfoModel.getName());
				for(String subCategory : categoryInfoModel.getSub_categories()){
					String[] subCategoriesArr = subCategory.split("%");					
					categoryAndSubCategoryInfo.addSubCategories(Arrays.asList(subCategoriesArr));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		responseEntity = new ResponseEntity<CategoryAndSubCategoryInfo>(categoryAndSubCategoryInfo, HttpStatus.OK);
		
		return responseEntity;
	}

}
