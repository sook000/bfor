package com.ssafy.bfor.home.model.service;

import java.util.List;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.travelinfo.model.TravelInfoDto;

public interface HomeService {

	List<FreeBoardDto> listFreeArticle() throws Exception;

	List<FreeBoardDto> listNoticeArticle() throws Exception;

	List<TravelInfoDto> listPlace(String sidoCode) throws Exception;

}
