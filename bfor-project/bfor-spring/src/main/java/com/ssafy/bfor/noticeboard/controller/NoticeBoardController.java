package com.ssafy.bfor.noticeboard.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.freeboard.model.FreeBoardListDto;
import com.ssafy.bfor.noticeboard.model.NoticeBoardDto;
import com.ssafy.bfor.noticeboard.model.NoticeBoardListDto;
import com.ssafy.bfor.noticeboard.model.service.NoticeBoardService;

@RestController
@RequestMapping("/noticeboard")
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
public class NoticeBoardController {
	
	private NoticeBoardService noticeBoardService;

	public NoticeBoardController(NoticeBoardService noticeBoardService) {
		this.noticeBoardService = noticeBoardService;
	}
	
	@GetMapping
	public ResponseEntity<?> listArticle(@RequestParam  Map<String, String> map) {
		try {
		NoticeBoardListDto noticeBoardListDto = noticeBoardService.listArticle(map);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return ResponseEntity.ok().headers(header).body(noticeBoardListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/{articleNo}")
	public ResponseEntity<NoticeBoardDto> getArticle(@PathVariable int articleNo) throws Exception {
		System.out.println("getArticle - 호출 : " + articleNo);
		return new ResponseEntity<NoticeBoardDto>(noticeBoardService.getArticle(articleNo), HttpStatus.OK);
	}
	
	@GetMapping("/modify/{articleno}")
	public ResponseEntity<NoticeBoardDto> getModifyArticle(@PathVariable int articleno) throws Exception {
		return new ResponseEntity<NoticeBoardDto>(noticeBoardService.getArticle(articleno), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> modifyArticle(@RequestBody NoticeBoardDto noticeBoardDto) throws Exception {
		System.out.println(noticeBoardDto.toString());
		noticeBoardService.modifyArticle(noticeBoardDto);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping
	public ResponseEntity<?> writeArticle(@RequestBody NoticeBoardDto noticeBoardDto) {
		try {
			noticeBoardService.writeArticle(noticeBoardDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(@PathVariable("articleno") int articleno) throws Exception {
		noticeBoardService.deleteArticle(articleno);
		return ResponseEntity.ok().build();
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
