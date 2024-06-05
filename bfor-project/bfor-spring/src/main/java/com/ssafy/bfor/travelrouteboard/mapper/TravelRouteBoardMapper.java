package com.ssafy.bfor.travelrouteboard.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.bfor.travelinfo.model.TravelInfoDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardListDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardOrderDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardThumbnailDto;

@Mapper
public interface TravelRouteBoardMapper {
	int insert(HashMap<String, Object> map);
	void insertTravelList(List<Object> list);
	List<TravelRouteBoardDto> getList(Map<String, Object> param);
	TravelRouteBoardDto read(int trbNo);
	Integer update(HashMap<String, String> map);
	Integer delete(int trbNo);
	int getTotalListCount(Map<String, Object> param);
	int hit(int trbNo);
	
	// 여행지 순서 
	void insertTravelOrder(Map<String, Object> map);
	TravelRouteBoardOrderDto readTravelOrder(int trbNo);
	void deleteTravelOrder(int trbNo);
	Integer getMaxTrbId(String userId);
	
	List<TravelInfoDto> getOrderInfo(int trbNo);
	List<TravelRouteBoardThumbnailDto> getThumbnailList(HashMap<String, String> map);
}
