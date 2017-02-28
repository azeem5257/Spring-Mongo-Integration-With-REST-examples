package com.bigbazaar.backend.repos;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bigbazaar.backend.model.SellerInfoModel;


@Repository
public interface SellerInfoRepository extends MongoRepository<SellerInfoModel, String> {
	GeoResults<SellerInfoModel> findByLocationNear(Point location, Distance distance);
}
