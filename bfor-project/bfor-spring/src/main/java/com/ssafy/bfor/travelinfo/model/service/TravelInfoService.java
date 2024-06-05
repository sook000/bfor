package com.ssafy.bfor.travelinfo.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.ssafy.bfor.travelinfo.model.TravelInfoDto;

public interface TravelInfoService {
	List<TravelInfoDto> getList(HashMap<String, String> map) throws Exception;
	TravelInfoDto detail(int contentId) throws Exception;
	List<TravelInfoDto> myFav(String userId);
	void clickFavorite(HashMap<String, Object> map);
	boolean checkFavorite(String userId, int contentId);
	List<TravelInfoDto> getBestList(String userId);
}
