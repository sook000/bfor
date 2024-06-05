package com.ssafy.bfor.travelrouteboard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatusCode;

import com.ssafy.bfor.travelinfo.model.TravelInfoDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardListDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardOrderDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardThumbnailDto;

public interface TravelRouteBoardService {
	Integer insert(HashMap<String, Object> map);
	void insertTravelList(List<Object> list);
	TravelRouteBoardListDto getList(Map<String, String> map);
	TravelRouteBoardDto read(int trbNo);
	Integer update(HashMap<String, String> map);
	Integer delete(int trbNo);
	
	// 여행지 순서 
	Integer getMaxTrbId(String userId);
	List<TravelInfoDto> getOrderInfo(int trbNo);
	Integer getNormalDistance(List<TravelInfoDto> list);
	Integer getDijkstraDistance(List<TravelInfoDto> list);
	List<TravelInfoDto> getDijkstraDistanceList(List<TravelInfoDto> list);
	List<TravelRouteBoardThumbnailDto> getThumbnailList(HashMap<String, String> map);
}
