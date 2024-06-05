package com.ssafy.bfor.user.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.bfor.user.model.FileInfoDto;
import com.ssafy.bfor.user.model.GugunDto;
import com.ssafy.bfor.user.model.SidoDto;
import com.ssafy.bfor.user.model.UserDto;

@Mapper
public interface UserMapper {
	Integer checkId(String userId) throws Exception;
	List<SidoDto> getSido() throws Exception;
	List<GugunDto> getGugun(int sidoCode) throws Exception;
	Integer registerUser(UserDto userdto) throws Exception;
	
	UserDto login(String userId) throws Exception;
	void saveRefreshToken(Map<String, String> map) throws Exception;
	UserDto userInfo(String userId) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(Map<String, String> map) throws Exception;
	UserDto findUserId(Map<String, String> map) throws Exception;
	UserDto authUser(Map<String, String> map) throws Exception;
	Integer modifyPwd(UserDto userDto);
	
	Integer writeFile(FileInfoDto fileInfoDto);

}
