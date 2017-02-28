package com.bigbazaar.backend.repos;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bigbazaar.backend.model.ProductInfoModel;


@Repository
public interface ProductRepository extends MongoRepository<ProductInfoModel, String>{

}
