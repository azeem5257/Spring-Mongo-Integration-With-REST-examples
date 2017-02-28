package com.bigbazaar.backend.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bigbazaar.backend.model.CategoryInfoModel;

@Repository
public interface CategoriesRepository extends MongoRepository<CategoryInfoModel, String>{

}
