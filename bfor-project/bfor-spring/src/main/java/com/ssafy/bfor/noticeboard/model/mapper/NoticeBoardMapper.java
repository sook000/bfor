package com.ssafy.bfor.noticeboard.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.noticeboard.model.NoticeBoardDto;

@Mapper
public interface NoticeBoardMapper {
	List<NoticeBoardDto> listArticle(Map<String, Object> param) throws Exception;
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	
	NoticeBoardDto getArticle(int articleNo) throws SQLException;
	
	Integer modifyArticle(NoticeBoardDto noticeBoardDto) throws Exception;
	Integer writeArticle(NoticeBoardDto noticeBoardDto) throws Exception;
	Integer deleteArticle(int articleNo) throws Exception;



}
