package com.ssafy.bfor.user.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.bfor.user.model.FileInfoDto;
import com.ssafy.bfor.user.model.GugunDto;
import com.ssafy.bfor.user.model.SidoDto;
import com.ssafy.bfor.user.model.UserDto;
import com.ssafy.bfor.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	private UserMapper userMapper;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Integer checkId(String userId) throws Exception {
		return userMapper.checkId(userId);
	}

	@Override
	public List<SidoDto> getSido() throws Exception {
		return userMapper.getSido();
	}

	@Override
	public List<GugunDto> getGugun(int sidoCode) throws Exception {
		return userMapper.getGugun(sidoCode);
	}

//	@Override
//	public Integer registerUser(UserDto userdto) throws Exception {
//		userdto.setUserPassword(passwordEncoder.encode(userdto.getUserPassword()));
//		return userMapper.registerUser(userdto);
//	}
	
	@Override
	public Integer registerUser(UserDto userdto) throws Exception {
		userdto.setUserPassword(passwordEncoder.encode(userdto.getUserPassword()));
		 FileInfoDto fileInfoDto = userdto.getProfileImage();
		 userMapper.registerUser(userdto);
		 if (fileInfoDto != null) {
			fileInfoDto.setUserId(userdto.getUserId());
			userMapper.writeFile(fileInfoDto);
	            
	        }
		return 1;
	}

	@Override
	public UserDto login(UserDto userDto) throws Exception {
		UserDto loginUser = userMapper.login(userDto.getUserId());
		if (loginUser != null && passwordEncoder.matches(userDto.getUserPassword(), loginUser.getUserPassword())) {
			return loginUser;
		} else {
			return null;
		}
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
	}

	@Override
	public UserDto userInfo(String userId) throws Exception {
		return userMapper.userInfo(userId);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return userMapper.getRefreshToken(userId);
	}

	@Override
	public void deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("token", null);
		userMapper.deleteRefreshToken(map);
	}

	@Override
	public String findUserId(Map<String, String> map) throws Exception {
		UserDto findUser = userMapper.findUserId(map);
		System.out.println("map출력:" + map.toString());
		System.out.println("findUser:" + findUser.toString());
		if (findUser != null) {
			return findUser.getUserId();
		} return null;
	}

	@Override
	public String authUser(Map<String, String> map) throws Exception {
		UserDto authUser = userMapper.authUser(map);
		if (authUser != null) {
			return authUser.getUserId();
		}
		return null;
	}

	@Override
	public Integer modifyPwd(UserDto userDto) throws Exception {
		userDto.setUserPassword(passwordEncoder.encode(userDto.getUserPassword()));
		return userMapper.modifyPwd(userDto);
	}
	
	
	

}
