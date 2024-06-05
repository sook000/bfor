package com.ssafy.bfor.travelinfo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bfor.travelinfo.model.TravelInfoDto;
import com.ssafy.bfor.travelinfo.model.mapper.TravelInfoMapper;

@Service
@Transactional
public class TravelInfoServiceImpl implements TravelInfoService {

	private TravelInfoMapper travelInfoMapper;
	
	public TravelInfoServiceImpl(TravelInfoMapper travelInfoMapper) {
		this.travelInfoMapper = travelInfoMapper;
	}
	
	@Override
	public List<TravelInfoDto> getList(HashMap<String, String> map) throws Exception {
		return travelInfoMapper.getList(map);
	}

	@Override
	public TravelInfoDto detail(int contentId) throws Exception {
		return travelInfoMapper.detail(contentId);
	}


	@Override
	public void clickFavorite(HashMap<String, Object> map) {

		boolean status = travelInfoMapper.getViewStatus(map);
		if (!status) {
			travelInfoMapper.insertFavorite(map);
		} else {
			travelInfoMapper.deleteFavorite(map);
		}
	}

	@Override
	public List<TravelInfoDto> myFav(String userId) {
		return travelInfoMapper.myFav(userId);
	}

	@Override
	public boolean checkFavorite(String userId, int contentId) {
		Map<String, Object> map = new HashMap<>();
		map.put("contentId", String.valueOf(contentId));
		map.put("userId", userId);
		return travelInfoMapper.getViewStatus(map);
	}

	@Override
	public List<TravelInfoDto> getBestList(String userId) {
		HashMap<String, Integer> map = travelInfoMapper.getUserSidoGugunCode(userId);
		return travelInfoMapper.getBestList(map);
	}


}
