package com.ssafy.bfor.freeboard.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.bfor.freeboard.model.FreeBoardCommentDto;
import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.freeboard.model.FreeBoardListDto;
import com.ssafy.bfor.freeboard.model.mapper.FreeBoardMapper;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {

	private FreeBoardMapper freeBoardMapper;
	
	@Autowired
	public FreeBoardServiceImpl(FreeBoardMapper freeBoardMapper) {
		this.freeBoardMapper = freeBoardMapper;
	}

	@Override
	public FreeBoardListDto listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null? "": map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);
		
		String key = map.get("key");
		param.put("key", key == null ? "" : key);
//		if ("user_id".equals(key))
//			param.put("key", key == null ? "" : "user_id");
		param.put("type", map.get("type") == null? "": map.get("type"));
		param.put("mypost", map.get("mypost") == null? "": map.get("mypost"));
		
		List<FreeBoardDto> list = freeBoardMapper.listArticle(param);
		System.out.println(key);
		System.out.println(map.get("word"));
		
		int totalArticleCount = freeBoardMapper.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		FreeBoardListDto freeBoardListDto = new FreeBoardListDto();
		freeBoardListDto.setArticles(list);
		freeBoardListDto.setCurrentPage(currentPage);
		freeBoardListDto.setTotalPageCount(totalPageCount);
		
		return freeBoardListDto;
	}
	
	@Override
	public FreeBoardDto getArticle(int articleNo) throws Exception {
		return freeBoardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		freeBoardMapper.updateHit(articleNo);
		
	}

	@Override
	public Integer modifyArticle(FreeBoardDto freeBoardDto) throws Exception {
		return freeBoardMapper.modifyArticle(freeBoardDto);
	}

	@Override
	public Integer writeArticle(FreeBoardDto freeBoardDto) throws Exception {
		return freeBoardMapper.writeArticle(freeBoardDto);
	}
	
	@Override
	public Integer deleteArticle(int fbNo) throws Exception {
		return freeBoardMapper.deleteArticle(fbNo);
	}

	// comment 관련
	@Override
	public List<FreeBoardCommentDto> listComment(int articleNo) throws Exception {
		return freeBoardMapper.listComment(articleNo);
	}

	@Override
	public Integer writeComment(FreeBoardCommentDto freeBoardCommentDto) throws Exception {
		return freeBoardMapper.writeComment(freeBoardCommentDto);
	}

	@Override
	public Integer modifyComment(FreeBoardCommentDto freeBoardCommentDto) throws Exception {
		return freeBoardMapper.modifyComment(freeBoardCommentDto);
	}

	@Override
	public Integer deleteComment(Map<String, String> map) {
		return freeBoardMapper.deleteComment(map);
	}

	@Override
	public Integer countComment(int articleNo) throws Exception {
		return freeBoardMapper.countComment(articleNo);
	}
	
	

}
