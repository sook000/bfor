package com.ssafy.bfor.home.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.home.model.mapper.HomeMapper;
import com.ssafy.bfor.travelinfo.model.TravelInfoDto;

@Service
public class HomeServiceImpl implements HomeService{

	private HomeMapper homeMapper;

	public HomeServiceImpl(HomeMapper homeMapper) {
		super();
		this.homeMapper = homeMapper;
	}

	@Override
	public List<FreeBoardDto> listFreeArticle() throws Exception {
		return homeMapper.listFreeArticle();
	}

	@Override
	public List<FreeBoardDto> listNoticeArticle() throws Exception {
		return homeMapper.listNoticeArticle();
	}

	@Override
	public List<TravelInfoDto> listPlace(String sidoCode) throws Exception {
		return homeMapper.listPlace(sidoCode);
	}
	
	
}
