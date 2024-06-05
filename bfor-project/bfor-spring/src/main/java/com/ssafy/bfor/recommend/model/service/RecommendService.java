package com.ssafy.bfor.recommend.model.service;

import com.ssafy.bfor.recommend.model.TravelInfoFavoriteDto;

public interface RecommendService {

	public TravelInfoFavoriteDto listPlaces(String userId) throws Exception;
}
