package com.ssafy.bfor.home.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bfor.freeboard.model.FreeBoardDto;
import com.ssafy.bfor.home.model.service.HomeService;
import com.ssafy.bfor.travelinfo.model.TravelInfoDto;

@RestController
@RequestMapping("/home")
public class HomeController {

	private HomeService homeService;

	public HomeController(HomeService homeService) {
		super();
		this.homeService = homeService;
	}
	
	@GetMapping("/free")
	public ResponseEntity<List<FreeBoardDto>> listFreeArticle() throws Exception {
		return new ResponseEntity<List<FreeBoardDto>>(homeService.listFreeArticle(), HttpStatus.OK);
	}
	
	@GetMapping("/notice")
	public ResponseEntity<List<FreeBoardDto>> listNoticeArticle() throws Exception {
		return new ResponseEntity<List<FreeBoardDto>>(homeService.listNoticeArticle(), HttpStatus.OK);
	}
	
	@GetMapping("/{sidoCode}")
	public ResponseEntity<List<TravelInfoDto>> listPlace(@PathVariable String sidoCode) throws Exception {
		return new ResponseEntity<>(homeService.listPlace(sidoCode), HttpStatus.OK);
	}
}
