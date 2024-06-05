package com.ssafy.bfor.freeboard.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ssafy.bfor.freeboard.model.FreeBoardCommentDto;
import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.freeboard.model.FreeBoardListDto;
import com.ssafy.bfor.freeboard.model.service.FreeBoardService;


@RestController
@RequestMapping("/freeboard")
//@CrossOrigin(origins = { "*" }, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
public class FreeBoardController {
	
	private FreeBoardService freeBoardService;
	
	@Autowired
	public FreeBoardController(FreeBoardService freeBoardService) {
		this.freeBoardService = freeBoardService;
	}
	
	@GetMapping
	public ResponseEntity<?> listArticle(@RequestParam  Map<String, String> map) {
		try {
			System.out.println("key" + map.get("key"));
			System.out.println("word" + map.get("word"));
			FreeBoardListDto freeboardListDto = freeBoardService.listArticle(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(freeboardListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/{articleNo}")
	public ResponseEntity<FreeBoardDto> getArticle(@PathVariable int articleNo) throws Exception {
		System.out.println("getArticle - 호출 : " + articleNo);
		freeBoardService.updateHit(articleNo);
		return new ResponseEntity<FreeBoardDto>(freeBoardService.getArticle(articleNo), HttpStatus.OK);
	}
	
	@GetMapping("/modify/{articleno}")
	public ResponseEntity<FreeBoardDto> getModifyArticle(@PathVariable int articleno) throws Exception {
		return new ResponseEntity<FreeBoardDto>(freeBoardService.getArticle(articleno), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> modifyArticle(@RequestBody FreeBoardDto freeBoardDto) throws Exception {
		System.out.println(freeBoardDto.toString());
		freeBoardService.modifyArticle(freeBoardDto);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping
	public ResponseEntity<?> writeArticle(@RequestBody FreeBoardDto freeBoardDto) {
		try {
			freeBoardService.writeArticle(freeBoardDto);
//			return ResponseEntity.ok().build();
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(@PathVariable("articleno") int articleno) throws Exception {
		freeBoardService.deleteArticle(articleno);
		return ResponseEntity.ok().build();
	}
	
	//comment 관련 --	
	@GetMapping("/comment/{articleno}")
	public ResponseEntity<?> listComment(@PathVariable("articleno") int articleno) throws Exception {
		return new ResponseEntity<List<FreeBoardCommentDto>>(freeBoardService.listComment(articleno), HttpStatus.OK);
	}
	
	@PostMapping("/comment")
	public ResponseEntity<?> writeComment(@RequestBody FreeBoardCommentDto freeBoardCommentDto) {
		try {
			freeBoardService.writeComment(freeBoardCommentDto);
//			return ResponseEntity.ok().build();
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	@PutMapping("/comment")
	public ResponseEntity<String> modifyComment(@RequestBody FreeBoardCommentDto freeBoardCommentDto) throws Exception {
		System.out.println(freeBoardCommentDto.toString());
		freeBoardService.modifyComment(freeBoardCommentDto);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping("/comment")
	public ResponseEntity<String> deleteComment(@RequestParam  Map<String, String> map) throws Exception {
		freeBoardService.deleteComment(map);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/comment/list/{articleno}")
	public ResponseEntity<?> countComment(@PathVariable("articleno") int articleno) throws Exception {
		return new ResponseEntity<Integer>(freeBoardService.countComment(articleno), HttpStatus.OK);
	}
	// 목록 출력 -----
//	@PostMapping("/create")
//	public ResponseEntity<?> create(@RequestBody FreeBoardDto freeBoardDto) throws Exception {
//		return new ResponseEntity<Integer>(freeBoardService.create(freeBoardDto), HttpStatus.OK);
//	}
//		@GetMapping("/list")
//		public ResponseEntity<?> list() throws Exception {
//			return new ResponseEntity<List<FreeBoardDto>>(freeBoardService.list(), HttpStatus.OK);
//		}
//	@GetMapping("/detail/{fbNo}")
//	public ResponseEntity<?> detail(@PathVariable int fbNo) throws Exception {
//		return new ResponseEntity<FreeBoardDto>(freeBoardService.detail(fbNo), HttpStatus.OK);
//	}
//	@GetMapping("/test")
//	public String test() {
//		return "test";
//	}
//	@GetMapping("/update/{no}")
//	public ResponseEntity<?> update(@PathVariable int fbNo) throws Exception {
//		return new ResponseEntity<FreeBoardDto>(freeBoardService.detail(fbNo), HttpStatus.OK);
//	}
//	@PostMapping("/create")
//	public ResponseEntity<?> create(@RequestBody FreeBoardDto freeBoardDto) throws Exception {
//		return new ResponseEntity<Integer>(freeBoardService.create(freeBoardDto), HttpStatus.OK);
//	}
//	@PutMapping("/update")
//	public ResponseEntity<?> update(@RequestBody FreeBoardDto freeBoardDto) throws Exception {
//		return new ResponseEntity<Integer>(freeBoardService.update(freeBoardDto), HttpStatus.OK);
//	}
//	
//	@DeleteMapping("/delete/{fbNo}")
//	public ResponseEntity<?> delte(@PathVariable int fbNo) throws Exception {
//		return new ResponseEntity<Integer>(freeBoardService.delete(fbNo), HttpStatus.OK);
//	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
