package com.bigbazaar.backend.services;

import org.springframework.http.ResponseEntity;

import com.bigbazaar.backend.domain.CategoryAndSubCategoryInfo;

public interface CategorySearchService {
	ResponseEntity<CategoryAndSubCategoryInfo> findAllCategoriesAndSubCategories();
}
