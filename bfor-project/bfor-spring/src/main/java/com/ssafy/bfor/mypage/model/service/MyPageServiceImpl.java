package com.ssafy.bfor.mypage.model.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.mypage.model.mapper.MyPageMapper;
import com.ssafy.bfor.travelinfo.model.TravelInfoDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardDto;
import com.ssafy.bfor.user.model.UserDto;

@Service
public class MyPageServiceImpl implements MyPageService{
	private MyPageMapper myPageMapper;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public MyPageServiceImpl(MyPageMapper myPageMapper, PasswordEncoder passwordEncoder) {
		super();
		this.myPageMapper = myPageMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<FreeBoardDto> listFreeArticle(String userId) throws Exception{
		return myPageMapper.listFreeArticle(userId);
	}

	@Override
	public Map<String, String> getSidoGugun(Map<String, String> map) throws Exception{
		Map<String, String> resultMap = new HashMap<>();
		String sidoName = myPageMapper.getSido(map.get("sidoCode"));
		String gugunName = myPageMapper.getGugun(map);
		resultMap.put("sidoName", sidoName);
		resultMap.put("gugunName", gugunName);
		return resultMap;
	}

	@Override
	public Integer modifyUser(UserDto userDto) throws Exception {
		userDto.setUserPassword(passwordEncoder.encode(userDto.getUserPassword()));
		return myPageMapper.modifyUser(userDto);
	}

	@Override
	public Boolean deleteUser(UserDto userDto) throws Exception {
		UserDto storedUser = myPageMapper.getUserById(userDto.getUserId());
		if (storedUser != null && passwordEncoder.matches(userDto.getUserPassword(), storedUser.getUserPassword())) {
			myPageMapper.deleteUser(userDto.getUserId());
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public List<FreeBoardDto> listZzim(String userId) throws Exception {
		return myPageMapper.listZzim(userId);
	}

	@Override
	public List<TravelRouteBoardDto> listroute(String userId) throws Exception {
		return myPageMapper.listroute(userId);
	}

	@Override
	public double calcLike(Map<String, String> map) throws Exception {
		String loginId = map.get("loginId");
		String otherId = map.get("otherId");
		
		List<String> places1 = myPageMapper.getPlaceList(loginId);
		List<String> places2 = myPageMapper.getPlaceList(otherId);
		
//		hashset을 이용해 개수 구하기 
		int count = getIntersectionCount(places1, places2);
		double c = (double) count/ places1.size();
		System.out.println(c);
		return (double) count / places1.size();
	}
	
	public int getIntersectionCount(List<String> list1, List<String> list2) {
        Set<String> set1 = new HashSet<>(list1);
        Set<String> set2 = new HashSet<>(list2);
        // Retain only the elements in set1 that are contained in set2
        set1.retainAll(set2);
        // The size of set1 is now the number of common elements
        return set1.size();
    }

}
