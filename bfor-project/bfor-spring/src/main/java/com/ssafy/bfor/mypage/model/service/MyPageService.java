package com.ssafy.bfor.mypage.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.travelinfo.model.TravelInfoDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardDto;
import com.ssafy.bfor.user.model.UserDto;

public interface MyPageService {

	List<FreeBoardDto> listFreeArticle(String userId) throws Exception;

	Map<String, String> getSidoGugun(Map<String, String> map) throws Exception;
	
	Integer modifyUser(UserDto userDto) throws Exception;

	Boolean deleteUser(UserDto userDto) throws Exception;

	List<FreeBoardDto> listZzim(String userId) throws Exception;

	List<TravelRouteBoardDto> listroute(String userId)throws Exception;

	double calcLike(Map<String, String> map) throws Exception;

}
