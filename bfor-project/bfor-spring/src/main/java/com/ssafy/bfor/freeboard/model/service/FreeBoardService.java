package com.ssafy.bfor.freeboard.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.bfor.freeboard.model.FreeBoardCommentDto;
import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.freeboard.model.FreeBoardListDto;

public interface FreeBoardService {
	
	FreeBoardListDto listArticle(Map<String, String> map) throws Exception;
	
	FreeBoardDto getArticle(int articleNo) throws Exception ;
	void updateHit(int articleNo) throws Exception;
	
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
