package com.ssafy.bfor.freeboard.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.bfor.freeboard.model.FreeBoardCommentDto;
import com.ssafy.bfor.freeboard.model.FreeBoardDto;

@Mapper
public interface FreeBoardMapper {
	List<FreeBoardDto> listArticle(Map<String, Object> param) throws SQLException;
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	
	FreeBoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;

	Integer modifyArticle(FreeBoardDto freeBoardDto) throws Exception;
	Integer writeArticle(FreeBoardDto freeBoardDto) throws Exception;
	Integer deleteArticle(int fbNo) throws Exception;
	
	//comment 관련
	List<FreeBoardCommentDto> listComment(int articleNo) throws Exception;
	Integer writeComment(FreeBoardCommentDto freeBoardCommentDto) throws Exception;
	Integer modifyComment(FreeBoardCommentDto freeBoardCommentDto) throws Exception;
	Integer deleteComment(Map<String, String> map);
	Integer countComment(int articleNo) throws Exception;
	
}
