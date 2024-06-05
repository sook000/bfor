package com.ssafy.bfor.recommend.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.bfor.travelinfo.model.TravelInfoDto;

@Mapper
public interface RecommendMapper {

//	 List<> zzimUserPlaces() throws Exception;

	List<String> userList() throws Exception;

	List<String> getPlaceList(String user) throws Exception;

	int getUserFavoriteType(String userId) throws Exception;

	List<String> getFavContentTypePlaces(int favContentTypeId) throws Exception;

	TravelInfoDto getInfo(String place) throws Exception;
	
}
