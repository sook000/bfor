package com.ssafy.bfor.mypage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.travelinfo.model.TravelInfoDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardDto;
import com.ssafy.bfor.user.model.UserDto;

@Mapper
public interface MyPageMapper {
	List<FreeBoardDto> listFreeArticle(String userId) throws Exception;

	String getSido(String string) throws Exception;
	String getGugun(Map<String, String> map) throws Exception;
	
	Integer modifyUser(UserDto userDto) throws Exception;
	
	UserDto getUserById(String userId) throws Exception;
	Integer deleteUser(String userId) throws Exception;

	List<FreeBoardDto> listZzim(String userId) throws Exception;

	List<TravelRouteBoardDto> listroute(String userId) throws Exception;; 
	List<String> getPlaceList(String user) throws Exception;
}
