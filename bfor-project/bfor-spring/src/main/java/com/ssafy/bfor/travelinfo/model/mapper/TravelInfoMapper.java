package com.ssafy.bfor.travelinfo.model.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.bfor.travelinfo.model.TravelInfoDto;

@Mapper
public interface TravelInfoMapper {
	List<TravelInfoDto> getList(HashMap<String, String> map) throws Exception;
	TravelInfoDto detail(int contentId) throws Exception;
	void addFavoriteCnt(String userId, int contentId);
	void insertFavorite(Map<String, Object> map);
	void deleteFavorite(Map<String, Object> map);
	boolean getViewStatus(Map<String, Object> map);
	List<TravelInfoDto> myFav(String userId);
	List<TravelInfoDto> getBestList(HashMap<String, Integer> map);
	HashMap<String, Integer> getUserSidoGugunCode(String userId);
	
}
