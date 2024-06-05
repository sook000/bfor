package com.ssafy.bfor.noticeboard.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.bfor.noticeboard.model.NoticeBoardDto;
import com.ssafy.bfor.noticeboard.model.NoticeBoardListDto;
import com.ssafy.bfor.noticeboard.model.mapper.NoticeBoardMapper;

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService{

	private NoticeBoardMapper noticeBoardMapper;

	public NoticeBoardServiceImpl(NoticeBoardMapper noticeBoardMapper) {
		this.noticeBoardMapper = noticeBoardMapper;
	}

	@Override
	public NoticeBoardListDto listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);
		
		List<NoticeBoardDto> list = noticeBoardMapper.listArticle(param);
		int totalArticleCount = noticeBoardMapper.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		
		NoticeBoardListDto noticeBoardListDto = new NoticeBoardListDto();
		noticeBoardListDto.setArticles(list);
		noticeBoardListDto.setCurrentPage(currentPage);
		noticeBoardListDto.setTotalPageCount(totalPageCount);
		
		return noticeBoardListDto;
	}

	@Override
	public NoticeBoardDto getArticle(int articleNo) throws Exception {
		return noticeBoardMapper.getArticle(articleNo);
	}

	@Override
	public Integer modifyArticle(NoticeBoardDto noticeBoardDto) throws Exception {
		return noticeBoardMapper.modifyArticle(noticeBoardDto);
	}

	@Override
	public Integer writeArticle(NoticeBoardDto noticeBoardDto) throws Exception {
		return noticeBoardMapper.writeArticle(noticeBoardDto);
	}

	@Override
	public Integer deleteArticle(int articleNo) throws Exception {
		return noticeBoardMapper.deleteArticle(articleNo);
	}
	
	
	
}
