package com.bigbazaar.backend.services;


import org.springframework.http.ResponseEntity;

import com.bigbazaar.backend.domain.ProductInfo;
import com.bigbazaar.backend.domain.ProductInfoResponse;

public interface ProductService {

	ResponseEntity<ProductInfo> create(ProductInfo user);
	ResponseEntity<ProductInfoResponse> findById(String id);
}
