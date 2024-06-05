package com.ssafy.bfor.user.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.bfor.user.model.GugunDto;
import com.ssafy.bfor.user.model.SidoDto;
import com.ssafy.bfor.user.model.UserDto;

public interface UserService {
	Integer checkId(String userId) throws Exception;
	List<SidoDto> getSido() throws Exception;
	List<GugunDto> getGugun(int sidoCode) throws Exception;
	Integer registerUser(UserDto userdto) throws Exception;
	
	UserDto login(UserDto userDto) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	UserDto userInfo(String userId) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(String userId) throws Exception;
	String findUserId(Map<String, String> map) throws Exception;
	String authUser(Map<String, String> map) throws Exception;
	Integer modifyPwd(UserDto userDto) throws Exception;
	
}
