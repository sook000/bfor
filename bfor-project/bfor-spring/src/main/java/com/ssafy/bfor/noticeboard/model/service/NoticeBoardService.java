package com.ssafy.bfor.noticeboard.model.service;

import java.util.Map;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.noticeboard.model.NoticeBoardDto;
import com.ssafy.bfor.noticeboard.model.NoticeBoardListDto;

public interface NoticeBoardService {
	NoticeBoardListDto listArticle(Map<String, String> map) throws Exception;
	NoticeBoardDto getArticle(int articleNo) throws Exception;
	
	Integer modifyArticle(NoticeBoardDto noticeBoardDto) throws Exception;
	Integer writeArticle(NoticeBoardDto noticeBoardDto) throws Exception;
	Integer deleteArticle(int articleNo) throws Exception;

}
