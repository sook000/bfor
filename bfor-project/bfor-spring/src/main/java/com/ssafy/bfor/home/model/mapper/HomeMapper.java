package com.ssafy.bfor.home.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.travelinfo.model.TravelInfoDto;

@Mapper
public interface HomeMapper {
	List<FreeBoardDto> listFreeArticle() throws Exception;

	List<FreeBoardDto> listNoticeArticle() throws Exception;

	List<TravelInfoDto> listPlace(String sidoCode) throws Exception;

}
