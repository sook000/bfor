package com.ssafy.bfor.travelinfo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bfor.travelinfo.model.TravelInfoDto;
import com.ssafy.bfor.travelinfo.model.service.TravelInfoService;

@RestController
@RequestMapping("/travelinfo")
//@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
//		RequestMethod.POST }, maxAge = 6000)
public class TravelInfoController {

	private TravelInfoService travelInfoService;

	@Autowired
	public TravelInfoController(TravelInfoService travelInfoService) {
		this.travelInfoService = travelInfoService;
	}

	@GetMapping("/search")
	public ResponseEntity<?> getList(@RequestParam HashMap<String, String> map) {
		try {
			return new ResponseEntity<List<TravelInfoDto>>(travelInfoService.getList(map), HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/{contentId}")
	public ResponseEntity<?> detail(@PathVariable int contentId) {
		try {
			return new ResponseEntity<TravelInfoDto>(travelInfoService.detail(contentId), HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/myFav")
	public ResponseEntity<?> myFav(@RequestParam String userId) {
		return new ResponseEntity<List<TravelInfoDto>>(travelInfoService.myFav(userId), HttpStatus.OK);
	}

	@PostMapping("/clickFav")
	public ResponseEntity<?> clickFavorite(@RequestBody HashMap<String, Object> map) {
		travelInfoService.clickFavorite(map);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/checkFav")
	public ResponseEntity<?> checkFavorite(@RequestParam String userId, int contentId) {
		return new ResponseEntity<Boolean> (travelInfoService.checkFavorite(userId, contentId), HttpStatus.OK);
	}
	
	@GetMapping("/best")
	public ResponseEntity<?> getBestList(@RequestParam String userId) {
		return new ResponseEntity<List<TravelInfoDto>> (travelInfoService.getBestList(userId), HttpStatus.OK);
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
