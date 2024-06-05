package com.ssafy.bfor.recommend.model;

import java.util.List;

import com.ssafy.bfor.travelinfo.model.TravelInfoDto;

import lombok.Data;

@Data
public class TravelInfoFavoriteDto {
	//가장 유사한 유저
	private String userId;
	//가장 유사한 유저와 몇 개 일치하는지
	private int count;
	//가장 좋아하는 카테고리
	private String favCategory;
	private List<TravelInfoDto> places;
	
}
